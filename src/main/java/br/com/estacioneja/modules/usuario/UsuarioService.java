package br.com.estacioneja.modules.usuario;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.errors.exceptions.DuplicateException;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.errors.exceptions.ForbiddenException;
import br.com.estacioneja.modules.usuario.dto.CreateUsuarioDto;
import br.com.estacioneja.modules.usuario.dto.ReadFotoPerfilDto;
import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.modules.usuario.dto.UpdateUsuarioDto;
import br.com.estacioneja.shared.storage.R2StorageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final R2StorageService r2StorageService;

    private static final long MAX_FOTO_SIZE_BYTES = 5L * 1024 * 1024;
    private static final Set<String> CONTENT_TYPES_PERMITIDOS = Set.of(
            "image/jpeg", "image/png", "image/webp"
    );
    private static final Duration FOTO_PERFIL_URL_TTL = Duration.ofMinutes(10);

    /* TRANSACOES */

    @Transactional
    public ReadUsuarioDto create(CreateUsuarioDto dto) {
        if (isEmailInUse(dto.email(), null))
            throw new DuplicateException("Email já está sendo Usado");

        if (isCpfInUse(dto.cpf(), null))
            throw new DuplicateException("CPF já está sendo Usado");

        Usuario newUsuario = new Usuario(dto.name(), dto.email(), dto.cpf(), dto.telefone(), dto.tipoUsuario());

        newUsuario.setSenha(passwordEncoder.encode(dto.senha()));

        this.usuarioRepository.save(newUsuario);

        return usuarioMapper.toDto(newUsuario);
    }

    @Transactional
    public void update(UUID id, UpdateUsuarioDto dto) {
        Usuario usuario = findEntityById(id);

        if (isEmailInUse(dto.email(), id))
            throw new DuplicateException("Email já está sendo usado");
        if (isCpfInUse(dto.cpf(), id))
            throw new DuplicateException("CPF já está sendo usado");

        usuario.updateData(dto.name(), dto.telefone(), dto.cpf(), dto.email());
    }

    @Transactional
    public void delete(UUID id) {
        Usuario usuario = findEntityById(id);

        String fotoKey = usuario.getFotoPerfilKey();
        usuarioRepository.delete(usuario);

        if (fotoKey != null && !fotoKey.isBlank()) {
            r2StorageService.delete(fotoKey);
        }
    }

    /* CONSULTAS */

    @Transactional(readOnly = true)
    public ReadUsuarioDto findById(UUID id) {
        return usuarioMapper.toDto(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public Usuario findEntityById(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    @Transactional(readOnly = true)
    public ReadUsuarioDto findByEmail(String email) {
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        return usuarioMapper.toDto(usuarioEncontrado);
    }

    /* FOTO DE PERFIL */

    @Transactional
    public ReadFotoPerfilDto uploadFotoPerfil(UUID id, MultipartFile file, Usuario usuarioAutenticado) {
        Usuario usuario = findEntityById(id);
        ensureCanManagePhoto(usuario, usuarioAutenticado);
        validarArquivoImagem(file);

        String extensao = resolverExtensao(file.getContentType());
        String novaKey = "usuarios/%s/perfil/%s.%s".formatted(usuario.getId(), UUID.randomUUID(), extensao);
        String keyAntiga = usuario.getFotoPerfilKey();

        r2StorageService.upload(novaKey, file);

        try {
            usuario.setFotoPerfilKey(novaKey);
            usuarioRepository.save(usuario);
        } catch (RuntimeException ex) {
            r2StorageService.delete(novaKey);
            throw ex;
        }

        if (keyAntiga != null && !keyAntiga.isBlank() && !keyAntiga.equals(novaKey)) {
            r2StorageService.delete(keyAntiga);
        }

        String url = r2StorageService.generatePresignedUrl(novaKey, FOTO_PERFIL_URL_TTL);
        return new ReadFotoPerfilDto(url, Instant.now().plus(FOTO_PERFIL_URL_TTL));
    }

    @Transactional(readOnly = true)
    public ReadFotoPerfilDto getFotoPerfil(UUID id) {
        Usuario usuario = findEntityById(id);
        String key = usuario.getFotoPerfilKey();

        if (key == null || key.isBlank()) {
            return new ReadFotoPerfilDto(null, null);
        }

        String url = r2StorageService.generatePresignedUrl(key, FOTO_PERFIL_URL_TTL);
        return new ReadFotoPerfilDto(url, Instant.now().plus(FOTO_PERFIL_URL_TTL));
    }

    @Transactional
    public void deleteFotoPerfil(UUID id, Usuario usuarioAutenticado) {
        Usuario usuario = findEntityById(id);
        ensureCanManagePhoto(usuario, usuarioAutenticado);

        String key = usuario.getFotoPerfilKey();
        if (key == null || key.isBlank()) return;

        usuario.setFotoPerfilKey(null);
        usuarioRepository.save(usuario);

        r2StorageService.delete(key);
    }

    /* VALIDAÇÕES */

    private void ensureCanManagePhoto(Usuario alvo, Usuario autenticado) {
        if (autenticado == null || !alvo.getId().equals(autenticado.getId())) {
            throw new ForbiddenException("Você não tem permissão para alterar a foto deste usuário");
        }
    }

    private void validarArquivoImagem(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("Arquivo de imagem é obrigatório");
        }
        if (file.getSize() > MAX_FOTO_SIZE_BYTES) {
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

    private boolean isEmailInUse(String email, UUID id) {
        return (id == null) ? usuarioRepository.existsByEmail(email) : usuarioRepository.existsByEmailAndIdNot(email, id);
    }

    private boolean isCpfInUse(String cpf, UUID id) {
        return (id == null) ? usuarioRepository.existsByCpf(cpf) : usuarioRepository.existsByCpfAndIdNot(cpf, id);
    }
}
