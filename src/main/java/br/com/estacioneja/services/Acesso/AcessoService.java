package br.com.estacioneja.services.Acesso;

import java.util.List;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.events.Empresa.EmpresaCriadaEvent;
import br.com.estacioneja.domain.events.Empresa.FilialCriadaEvent;
import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Acesso.TipoAcesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Acesso.AcessoRepository;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.exceptions.custom.AccessNotFoundException;
import br.com.estacioneja.infra.config.mapper.AcessoMapper;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Filial.FilialService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.usecases.interfaces.IAcesso;
import jakarta.transaction.Transactional;

@Service
public class AcessoService implements IAcesso {
    private final AcessoRepository acessoRepository;
    private final UsuarioService usuarioService;
    private final FilialService filialService;
    private final EmpresaService empresaService;
    private final AcessoMapper acessoMapper;

    public AcessoService(AcessoRepository acessoRepository, UsuarioService usuarioService, FilialService FilialService, EmpresaService empresaService, AcessoMapper acessoMapper) {
        this.acessoRepository = acessoRepository;
        this.usuarioService = usuarioService;
        this.filialService = FilialService;
        this.empresaService = empresaService;
        this.acessoMapper = acessoMapper;
    }

    /* TRANSACOES */

    @Override @Transactional
    public AcessoOutputDTO createFilial(AcessoDTO dto, UUID filialId)  {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());
        Filial filial = filialService.findEntityById(filialId);

        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, filial);

        return acessoMapper.toDto(acessoRepository.save(newAcesso));
    }  

    @Override @Transactional
    public AcessoOutputDTO createEmpresa(AcessoDTO dto, Long empresaId)  {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());
        Empresa empresa = empresaService.findEntityById(empresaId);

        Acesso newAcesso = new Acesso(dto.tipoAcesso(), usuario, empresa);

        return acessoMapper.toDto(acessoRepository.save(newAcesso));
    }  

    @Override @Transactional
    public void update(UUID id, AcessoDTO dto) {
        Acesso acesso = findEntityById(id);

        acesso.setTipoAcesso(dto.tipoAcesso());
        
        acessoRepository.save(acesso);
    }

    @Override @Transactional
    public void delete(UUID id) {
        acessoRepository.deleteById(id);
    }

    @EventListener
    public void handleEventFilialCriada(FilialCriadaEvent filialCriadaEvent) {
        createFilial(new AcessoDTO(TipoAcesso.MASTER, filialCriadaEvent.representanteId()), filialCriadaEvent.filialId());
    }

    @EventListener
    public void handleEventEmpresaCriada(EmpresaCriadaEvent empresaCriadaEvent) {
        createEmpresa(new AcessoDTO(TipoAcesso.MASTER, empresaCriadaEvent.representanteId()), empresaCriadaEvent.empresaId());
    }

    /* CONSULTAS */

    @Override
    public Acesso findEntityById(UUID id) {
        return acessoRepository.findById(id).orElseThrow(AccessNotFoundException::new);
    }

    @Override @Transactional
    public AcessoOutputDTO findById(UUID id) {
        return acessoMapper.toDto(findEntityById(id));
    }

    @Override
    public List<AcessoOutputDTO> findAccessByFilial(UUID filialId) {
        Filial filial = filialService.findEntityById(filialId);

        return acessoMapper.toDtoList(this.acessoRepository.findAllByFilial(filial));
    }

    @Override
    public Acesso findAccessByUserAndFilial(Usuario usuario, Filial filial) {
        Acesso acesso = this.acessoRepository.findByUsuarioAndFilial(usuario, filial);

        if(acesso == null) throw new AccessNotFoundException();

        return acesso;
    }

    @Override
    public List<UsuarioOutputDTO> findAllUsersByFilial(UUID filialId) {
        return this.usuarioService.toDtoList(this.acessoRepository.findAllUsersByFilial(filialId));
    }

    @Override
    public AcessoOutputDTO create(AcessoDTO dto) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }



}
