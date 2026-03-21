package br.com.estacioneja.services.Acesso;

import java.util.List;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.enums.TipoAcesso;
import br.com.estacioneja.domain.events.Empresa.EmpresaCriadaEvent;
import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Acesso.AcessoRepository;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.update.AcessoUpdateDto;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.AcessoMapper;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IAcesso;
import jakarta.transaction.Transactional;

@Service
public class AcessoService implements IAcesso {
    private final AcessoRepository acessoRepository;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final AcessoMapper acessoMapper;

    public AcessoService(AcessoRepository acessoRepository, UsuarioService usuarioService, EmpresaService empresaService, AcessoMapper acessoMapper) {
        this.acessoRepository = acessoRepository;
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.acessoMapper = acessoMapper;
    }

    /* TRANSACOES */

    @Override @Transactional
    public AcessoOutputDTO create(AcessoDTO dto, UUID empresaId)  {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());
        Empresa empresa = empresaService.findEntityById(empresaId);

        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, empresa);

        return acessoMapper.toDto(acessoRepository.save(newAcesso));
    }  

    @Override @Transactional
    public void update(UUID id, AcessoUpdateDto dto) {
        Acesso acesso = findEntityById(id);

        acesso.setTipoAcesso(dto.tipoAcesso());
        
        acessoRepository.save(acesso);
    }

    @Override @Transactional
    public void delete(UUID id) {
        acessoRepository.deleteById(id);
    }


    @EventListener
    public void handleEventEmpresaCriada(EmpresaCriadaEvent empresaCriadaEvent) {
        this.create(new AcessoDTO(TipoAcesso.MASTER, empresaCriadaEvent.representanteId()), empresaCriadaEvent.empresaId());
    }

    /* CONSULTAS */

    @Override
    public Acesso findEntityById(UUID id) {
        return acessoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado"));
    }

    @Override @Transactional
    public AcessoOutputDTO findById(UUID id) {
        return acessoMapper.toDto(findEntityById(id));
    }

    @Override
    public List<AcessoOutputDTO> findAccessByEmpresa(UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);

        return acessoMapper.toDtoList(this.acessoRepository.findAllByEmpresa(empresa));
    }

    @Override
    public List<AcessoOutputDTO> findAccessByUser(Usuario usuario) {
        return acessoMapper.toDtoList(this.acessoRepository.findAllByUsuario(usuario));
    }


    @Override
    public AcessoOutputDTO findAccessByUserAndEmpresaId(Usuario usuario, UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);

        return  acessoMapper.toDto(acessoRepository.findByUsuarioAndEmpresa(usuario, empresa));
    }

    @Override
    public List<Usuario> findAllUsersByEmpresa(UUID empresaId) {
        return this.acessoRepository.findAllUsersByEmpresa(empresaId);
    }

}
