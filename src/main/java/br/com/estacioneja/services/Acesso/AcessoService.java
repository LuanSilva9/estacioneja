package br.com.estacioneja.services.Acesso;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.domain.enums.TipoAcesso;
import br.com.estacioneja.domain.enums.TipoUsuario;
import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Acesso.Actor;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Acesso.AcessoRepository;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.update.AcessoUpdateDto;
import br.com.estacioneja.exceptions.custom.BusinessException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.AcessoMapper;
import br.com.estacioneja.infra.config.security.AuthorizationService;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IAcesso;

@Service
@RequiredArgsConstructor
public class AcessoService implements IAcesso {
    private final AcessoRepository acessoRepository;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final AcessoMapper acessoMapper;
    private final AuthorizationService authorizationService;

    /* TRANSACOES */

    @Override
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

    @Override @Transactional
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

    @Override @Transactional
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

    @Override @Transactional(readOnly = true)
    public Acesso findEntityById(UUID id) {
        return acessoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado"));
    }

    @Override @Transactional(readOnly = true)
    public AcessoOutputDTO findById(UUID id) {
        return acessoMapper.toDto(findEntityById(id));
    }

    @Override @Transactional(readOnly = true)
    public List<AcessoOutputDTO> findAccessByEmpresa(UUID empresaId) {
        Usuario autenticado = authorizationService.getCurrentUser();
        authorizationService.requireEmpresaRole(autenticado, empresaId, TipoAcesso.MASTER);

        Empresa empresa = empresaService.findEntityById(empresaId);
        return acessoMapper.toDtoList(this.acessoRepository.findAllByEmpresa(empresa));
    }

    @Override @Transactional(readOnly = true)
    public List<AcessoOutputDTO> findAccessByUser(Usuario usuario) {
        return acessoMapper.toDtoList(this.acessoRepository.findAllByUsuario(usuario));
    }

 
    @Override @Transactional(readOnly = true)
    public AcessoOutputDTO findAccessByUserAndEmpresaId(Usuario usuario, UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);

        return  acessoMapper.toDto(acessoRepository.findByUsuarioAndEmpresa(usuario, empresa));
    }

    @Override @Transactional(readOnly = true)
    public List<Usuario> findAllUsersByEmpresa(UUID empresaId) {
        return this.acessoRepository.findAllUsersByEmpresa(empresaId);
    }

}
