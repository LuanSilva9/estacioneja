package br.com.estacioneja.services.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.events.Estacionamento.EstacionamentoCriadoEvent;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.exceptions.custom.ParkNotFoundException;
import br.com.estacioneja.infra.config.mapper.EstacionamentoMapper;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Endereco.EnderecoService;
import br.com.estacioneja.services.Filial.FilialService;
import br.com.estacioneja.usecases.interfaces.IEstacionamento;
import jakarta.transaction.Transactional;

@Service
public class EstacionamentoService implements IEstacionamento {
    private final EstacionamentoRepository estacionamentoRepository;
    private final FilialService filialService;
    private final AcessoService acessoService;
    private final ApplicationEventPublisher eventPublisher;
    private final EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoService(EstacionamentoRepository estacionamentoRepository, EmpresaService empresaService, EnderecoService enderecoService, EstacionamentoMapper estacionamentoMapper, AcessoService acessoService, ApplicationEventPublisher eventPublisher, FilialService filialService) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.filialService = filialService;
        this.estacionamentoMapper = estacionamentoMapper;
        this.eventPublisher = eventPublisher;
        this.acessoService = acessoService;
    }

    /* TRANSACOES */

    @Override @Transactional
    public EstacionamentoOutputDTO create(EstacionamentoDTO dto) {
        Filial filial = filialService.findEntityById(dto.filialId());

        Estacionamento newEstacionamento = new Estacionamento(dto, filial);

        Estacionamento saved = estacionamentoRepository.save(newEstacionamento);

        List<UsuarioOutputDTO> usuariosPorEmpresa = acessoService.findAllUsersByFilial(dto.filialId());
        
        eventPublisher.publishEvent(new EstacionamentoCriadoEvent(usuariosPorEmpresa, newEstacionamento.getId()));

        return estacionamentoMapper.toDto(saved);
    }
    
    @Override @Transactional
    public void update(UUID id, EstacionamentoDTO dto) {
        Filial filial = filialService.findEntityById(dto.filialId());

        Estacionamento estacionamento = findEntityById(id);

        estacionamento.setFilial(filial);
        estacionamento.setPrivacidade(dto.privacidade());

        estacionamentoRepository.save(estacionamento);
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

}
