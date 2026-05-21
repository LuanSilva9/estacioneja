package br.com.estacioneja.services.Empresa;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.estacioneja.domain.enums.TipoAcesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Empresa.EmpresaRepository;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.dto.output.URLImagemOutputDTO;
import br.com.estacioneja.dto.update.EmpresaUpdateDto;
import br.com.estacioneja.exceptions.custom.BusinessException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.EmpresaMapper;
import br.com.estacioneja.infra.config.security.AuthorizationService;
import br.com.estacioneja.services.Endereco.EnderecoService;
import br.com.estacioneja.services.Storage.R2StorageService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IEmpresa;


@Service
@RequiredArgsConstructor
public class EmpresaService implements IEmpresa {
    private final EmpresaRepository empresaRepository;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private final EmpresaMapper empresaMapper;
    private final R2StorageService r2StorageService;
    private final AuthorizationService authorizationService;

    private static final long MAX_IMAGE_SIZE_BYTES = 5L * 1024 * 1024;
    private static final Set<String> CONTENT_TYPES_PERMITIDOS = Set.of(
            "image/jpeg", "image/png", "image/webp"
    );
    private static final Duration IMAGE_URL_TTL = Duration.ofMinutes(10);

    /* TRANSACOES */
    
    @Override @Transactional
    public EmpresaOutputDTO create(EmpresaDTO dto) {
        Usuario representante = usuarioService.findEntityById(dto.representanteId());
        Endereco endereco = enderecoService.create(dto.endereco());

        Empresa empresaPai = null;
    
        if (dto.empresaId() != null) {
            empresaPai = findEntityById(dto.empresaId());
        }

        Empresa newEmpresa;

        if (empresaPai != null) {
            newEmpresa = Empresa.criarFilial(
                dto.nome(), endereco, dto.tipoEmpresa(),
                dto.cnpj(), dto.prefixo(), dto.plano(),
                empresaPai, representante
            );
        } else {
            newEmpresa = Empresa.criarMatriz(
                representante, dto.nome(), endereco,
                dto.tipoEmpresa(), dto.cnpj(),
                dto.prefixo(), dto.plano()
            );
        }

        empresaRepository.save(newEmpresa);

        return empresaMapper.toDto(newEmpresa);
    }

    @Override @Transactional
    public void update(UUID id, EmpresaUpdateDto dto) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, id, TipoAcesso.MASTER);

        Empresa empresa = findEntityById(id);
        empresa.setNome(dto.nome());
        empresa.setTipoEmpresa(dto.tipoEmpresa());
    }

    @Override @Transactional
    public void delete(UUID id) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, id, TipoAcesso.MASTER);

        Empresa empresa = findEntityById(id);
        empresaRepository.delete(empresa);
    }
    
    /* CONSULTAS */
    
    @Override @Transactional(readOnly = true)
    public Empresa findEntityById(UUID empresaId) {
        return empresaRepository.findById(empresaId).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada")); 
    }
    
    @Override @Transactional(readOnly = true)
    public EmpresaOutputDTO findById(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaAccess(autenticado, empresaId);
        return empresaMapper.toDto(findEntityById(empresaId));
    }
    
    
    @Override @Transactional(readOnly = true)
    public List<EmpresaOutputDTO> findAll() {
        return empresaMapper.toDtoList(empresaRepository.findAll());
    }

    /* LOGO */

    @Override @Transactional
    public URLImagemOutputDTO uploadLogo(UUID id, MultipartFile file, Usuario usuarioAutenticado) {
        authorizationService.requireEmpresaRole(usuarioAutenticado, id, TipoAcesso.MASTER);
        Empresa empresa = findEntityById(id);
        validarArquivoImagem(file);

        String extensao = resolverExtensao(file.getContentType());
        String novaKey = "empresas/%s/logo/%s.%s".formatted(empresa.getId(), UUID.randomUUID(), extensao);
        String keyAntiga = empresa.getLogotipoEmpresa();

        r2StorageService.upload(novaKey, file);

        try {
            empresa.setLogotipoEmpresa(novaKey);
            empresaRepository.save(empresa);
        } catch (RuntimeException ex) {
            r2StorageService.delete(novaKey);
            throw ex;
        }

        if (keyAntiga != null && !keyAntiga.isBlank() && !keyAntiga.equals(novaKey)) {
            r2StorageService.delete(keyAntiga);
        }

        return presignedUrl(novaKey);
    }

    @Override @Transactional(readOnly = true)
    public URLImagemOutputDTO getLogo(UUID id) {
        return presignedUrlOrEmpty(findEntityById(id).getLogotipoEmpresa());
    }

    @Override @Transactional
    public void deleteLogo(UUID id, Usuario usuarioAutenticado) {
        authorizationService.requireEmpresaRole(usuarioAutenticado, id, TipoAcesso.MASTER);
        Empresa empresa = findEntityById(id);

        String key = empresa.getLogotipoEmpresa();
        if (key == null || key.isBlank()) return;

        empresa.setLogotipoEmpresa(null);
        empresaRepository.save(empresa);

        r2StorageService.delete(key);
    }

    /* BANNER */

    @Override @Transactional
    public URLImagemOutputDTO uploadBanner(UUID id, MultipartFile file, Usuario usuarioAutenticado) {
        authorizationService.requireEmpresaRole(usuarioAutenticado, id, TipoAcesso.MASTER);
        Empresa empresa = findEntityById(id);
        validarArquivoImagem(file);

        String extensao = resolverExtensao(file.getContentType());
        String novaKey = "empresas/%s/banner/%s.%s".formatted(empresa.getId(), UUID.randomUUID(), extensao);
        String keyAntiga = empresa.getBannerEmpresa();

        r2StorageService.upload(novaKey, file);

        try {
            empresa.setBannerEmpresa(novaKey);
            empresaRepository.save(empresa);
        } catch (RuntimeException ex) {
            r2StorageService.delete(novaKey);
            throw ex;
        }

        if (keyAntiga != null && !keyAntiga.isBlank() && !keyAntiga.equals(novaKey)) {
            r2StorageService.delete(keyAntiga);
        }

        return presignedUrl(novaKey);
    }

    @Override @Transactional(readOnly = true)
    public URLImagemOutputDTO getBanner(UUID id) {
        return presignedUrlOrEmpty(findEntityById(id).getBannerEmpresa());
    }

    @Override @Transactional
    public void deleteBanner(UUID id, Usuario usuarioAutenticado) {
        authorizationService.requireEmpresaRole(usuarioAutenticado, id, TipoAcesso.MASTER);
        Empresa empresa = findEntityById(id);

        String key = empresa.getBannerEmpresa();
        if (key == null || key.isBlank()) return;

        empresa.setBannerEmpresa(null);
        empresaRepository.save(empresa);

        r2StorageService.delete(key);
    }

    /* VALIDAÇÕES */

    private void validarArquivoImagem(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("Arquivo de imagem é obrigatório");
        }
        if (file.getSize() > MAX_IMAGE_SIZE_BYTES) {
            throw new BusinessException("Arquivo excede o tamanho máximo de 5MB");
        }
        String contentType = file.getContentType();
        if (contentType == null || !CONTENT_TYPES_PERMITIDOS.contains(contentType.toLowerCase())) {
            throw new BusinessException("Formato inválido. Use JPEG, PNG ou WEBP");
        }
    }

    private String resolverExtensao(String contentType) {
        return switch (contentType.toLowerCase()) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            default -> throw new BusinessException("Formato inválido. Use JPEG, PNG ou WEBP");
        };
    }

    private URLImagemOutputDTO presignedUrl(String key) {
        String url = r2StorageService.generatePresignedUrl(key, IMAGE_URL_TTL);
        return new URLImagemOutputDTO(url, Instant.now().plus(IMAGE_URL_TTL));
    }

    private URLImagemOutputDTO presignedUrlOrEmpty(String key) {
        if (key == null || key.isBlank()) {
            return new URLImagemOutputDTO(null, null);
        }
        return presignedUrl(key);
    }
}
