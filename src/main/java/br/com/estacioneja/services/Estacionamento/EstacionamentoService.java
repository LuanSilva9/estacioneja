package br.com.estacioneja.services.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.events.Estacionamento.EstacionamentoCriadoEvent;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.exceptions.custom.ParkNotFoundException;
import br.com.estacioneja.infra.config.mapper.EstacionamentoMapper;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Endereco.EnderecoService;
import br.com.estacioneja.usecases.interfaces.IEstacionamento;
import jakarta.transaction.Transactional;

@Service
public class EstacionamentoService implements IEstacionamento {
    private final EstacionamentoRepository estacionamentoRepository;
    private final EmpresaService empresaService;
    private final AcessoService acessoService;
    private final EnderecoService enderecoService;
    private final ApplicationEventPublisher eventPublisher;
    private final EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoService(EstacionamentoRepository estacionamentoRepository, EmpresaService empresaService, EnderecoService enderecoService, EstacionamentoMapper estacionamentoMapper, AcessoService acessoService, ApplicationEventPublisher eventPublisher) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.empresaService = empresaService;
        this.estacionamentoMapper = estacionamentoMapper;
        this.eventPublisher = eventPublisher;
        this.acessoService = acessoService;
        this.enderecoService = enderecoService;
    }

    /* TRANSACOES */

    @Override @Transactional
    public EstacionamentoOutputDTO create(EstacionamentoDTO dto) {
        Empresa empresa = empresaService.findEntityById(dto.empresaId());

        Endereco endereco = enderecoService.createEntity(dto.endereco());

        Estacionamento newEstacionamento = new Estacionamento(dto, endereco, empresa);

        Estacionamento saved = estacionamentoRepository.save(newEstacionamento);

        List<UsuarioOutputDTO> usuariosPorEmpresa = acessoService.findAllUsersByEmpresa(dto.empresaId());
        
        eventPublisher.publishEvent(new EstacionamentoCriadoEvent(usuariosPorEmpresa, newEstacionamento.getId()));

        return estacionamentoMapper.toDto(saved);
    }
    
    @Override @Transactional
    public EstacionamentoOutputDTO update(UUID id, EstacionamentoDTO dto) {
        Empresa empresa = empresaService.findEntityById(dto.empresaId());

        Estacionamento estacionamento = findEntityById(id);

        estacionamento.setEmpresa(empresa);
        estacionamento.setPrivacidade(dto.privacidade());

        return estacionamentoMapper.toDto(estacionamentoRepository.save(estacionamento));
    }

    @Override @Transactional
    public void delete(UUID id) {
        Estacionamento estacionamento = findEntityById(id);

        estacionamentoRepository.delete(estacionamento);
    }

    /* CONSULTAS */
    
    @Override
    public EstacionamentoOutputDTO findById(UUID idEstacionamento) {
        return estacionamentoMapper.toDto(findEntityById(idEstacionamento));
    }

    @Override
    public Estacionamento findEntityById(UUID idEstacionamento) {
       return estacionamentoRepository.findById(idEstacionamento).orElseThrow(ParkNotFoundException::new); 
    }

    @Override
    public List<EstacionamentoOutputDTO> findEstacionamentoByEmpresa(Long idEmpresa) {
        Empresa empresa = empresaService.findEntityById(idEmpresa);

        return estacionamentoMapper.toDtoList(estacionamentoRepository.findAllByEmpresa(empresa));
    }

}
