package br.com.estacioneja.modules.acessos;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.shared.enums.TipoAcesso;
import br.com.estacioneja.shared.enums.TipoUsuario;
import br.com.estacioneja.modules.acessos.dto.AcessoDTO;
import br.com.estacioneja.modules.acessos.dto.AcessoOutputDTO;
import br.com.estacioneja.modules.acessos.dto.AcessoUpdateDto;
import br.com.estacioneja.modules.empresa.Empresa;
import br.com.estacioneja.modules.empresa.EmpresaService;
import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.infra.config.security.AuthorizationService;
import br.com.estacioneja.modules.usuario.UsuarioService;

@Service
@RequiredArgsConstructor
public class AcessoService  {
    private final AcessoRepository acessoRepository;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final AcessoMapper acessoMapper;
    private final AuthorizationService authorizationService;

    /* TRANSACOES */

    @Transactional
    public AcessoOutputDTO create(Actor actor, AcessoDTO dto, UUID empresaId) {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());

        if (!actor.isSistema()) {
            Usuario autenticado = authorizationService.getCurrentUser();
            if (!autenticado.getId().equals(actor.getUsuarioId())) {
                throw new BusinessException("Actor inconsistente com usuário autenticado.");
            }
            authorizationService.requireEmpresaRole(autenticado, empresaId, TipoAcesso.MASTER);

            if (usuario.getId().equals(actor.getUsuarioId())) {
                throw new BusinessException("Você não pode criar suas próprias permissões!");
            }
        }

        if (usuario.getTipoUsuario() == TipoUsuario.COMUM) {
            throw new BusinessException("Usuário beneficiado deve ser administrativo.");
        }

        Empresa empresa = empresaService.findEntityById(empresaId);
        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, empresa);
        return acessoMapper.toDto(acessoRepository.save(newAcesso));
    }

    @Transactional
    public void update(UUID id, AcessoUpdateDto dto) {
        Acesso acesso = findEntityById(id);
        UUID empresaId = acesso.getEmpresa() == null ? null : acesso.getEmpresa().getId();

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaId, TipoAcesso.MASTER);

        if (acesso.getUsuario() != null && acesso.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não pode alterar o seu próprio acesso.");
        }

        acesso.setTipoAcesso(dto.tipoAcesso());
        acessoRepository.save(acesso);
    }

    @Transactional
    public void delete(UUID id) {
        Acesso acesso = findEntityById(id);
        UUID empresaId = acesso.getEmpresa() == null ? null : acesso.getEmpresa().getId();

        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaId, TipoAcesso.MASTER);

        if (acesso.getUsuario() != null && acesso.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não pode revogar o seu próprio acesso.");
        }

        acessoRepository.delete(acesso);
    }

    /* CONSULTAS */

    @Transactional(readOnly = true)
    public Acesso findEntityById(UUID id) {
        return acessoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado"));
    }

    @Transactional(readOnly = true)
    public AcessoOutputDTO findById(UUID id) {
        return acessoMapper.toDto(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<AcessoOutputDTO> findAccessByEmpresa(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaId, TipoAcesso.MASTER);

        Empresa empresa = empresaService.findEntityById(empresaId);
        return acessoMapper.toDtoList(this.acessoRepository.findAllByEmpresa(empresa));
    }

    @Transactional(readOnly = true)
    public List<AcessoOutputDTO> findAccessByUser(Usuario usuario) {
        return acessoMapper.toDtoList(this.acessoRepository.findAllByUsuario(usuario));
    }

 
    @Transactional(readOnly = true)
    public AcessoOutputDTO findAccessByUserAndEmpresaId(Usuario usuario, UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);

        return  acessoMapper.toDto(acessoRepository.findByUsuarioAndEmpresa(usuario, empresa));
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAllUsersByEmpresa(UUID empresaId) {
        return this.acessoRepository.findAllUsersByEmpresa(empresaId);
    }

}
