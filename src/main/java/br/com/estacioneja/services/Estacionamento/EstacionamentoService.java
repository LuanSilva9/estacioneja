package br.com.estacioneja.services.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.enums.Privacidade;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.dto.update.EstacionamentoUpdateDto;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.exceptions.custom.ParkSizeViolatedException;
import br.com.estacioneja.infra.config.mapper.EstacionamentoMapper;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.services.Endereco.EnderecoService;
import br.com.estacioneja.usecases.interfaces.IEstacionamento;
import jakarta.transaction.Transactional;

@Service
public class EstacionamentoService implements IEstacionamento {
    private final EstacionamentoRepository estacionamentoRepository;
    private final EmpresaService empresaService;
    private final EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoService(EstacionamentoRepository estacionamentoRepository, EmpresaService empresaService, EnderecoService enderecoService, EstacionamentoMapper estacionamentoMapper) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.empresaService = empresaService;
        this.estacionamentoMapper = estacionamentoMapper;
    }

    /* TRANSACOES */

    @Override @Transactional
    public EstacionamentoOutputDTO create(EstacionamentoDTO dto) {
        Empresa empresa = empresaService.findEntityById(dto.empresaId());

        Estacionamento newEstacionamento = new Estacionamento(dto, empresa);

        Estacionamento saved = estacionamentoRepository.save(newEstacionamento);

        return estacionamentoMapper.toDto(saved);
    }
    
    @Override @Transactional
    public void update(UUID id, EstacionamentoUpdateDto dto) {
        Estacionamento estacionamento = findEntityById(id);
 
        estacionamento.setPrivacidade(dto.privacidade());

        estacionamentoRepository.save(estacionamento);
    }

    @Override @Transactional
    public void delete(UUID id) {
        Estacionamento estacionamento = findEntityById(id);

        estacionamentoRepository.delete(estacionamento);
    }

    public void atualizarCapacidadeDisponivel(UUID estacionamentoId, Long novaCapacidade) {
        Estacionamento estacionamento = findEntityById(estacionamentoId);

        if(novaCapacidade < 0 ||novaCapacidade > estacionamento.getCapacidade()) throw new ParkSizeViolatedException();

        estacionamento.setCapacidadeDisponivel(novaCapacidade);
    }

    /* CONSULTAS */

    @Override
    public List<EstacionamentoOutputDTO> findByPrivacidade(Privacidade privacidade) {
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findByPrivacidade(privacidade));
    }

    @Override
    public List<EstacionamentoOutputDTO> findByEmpresa(UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);
        
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findAllByEmpresa(empresa));
    }


    @Override
    public EstacionamentoOutputDTO findById(UUID idEstacionamento) {
        return estacionamentoMapper.toDto(findEntityById(idEstacionamento));
    }

    @Override
    public Estacionamento findEntityById(UUID idEstacionamento) {
       return estacionamentoRepository.findById(idEstacionamento).orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado")); 
    }

}
