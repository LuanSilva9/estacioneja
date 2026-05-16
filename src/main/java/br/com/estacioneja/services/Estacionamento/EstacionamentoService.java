package br.com.estacioneja.services.Estacionamento;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.domain.enums.Privacidade;
import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.dto.update.EstacionamentoUpdateDto;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.EstacionamentoMapper;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.usecases.interfaces.IEstacionamento;

@Service
@RequiredArgsConstructor
public class EstacionamentoService implements IEstacionamento {
    private final EstacionamentoRepository estacionamentoRepository;
    private final EmpresaService empresaService;
    private final EstacionamentoMapper estacionamentoMapper;

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


    /* CONSULTAS */

    @Override @Transactional(readOnly = true)
    public List<EstacionamentoOutputDTO> findByPrivacidade(Privacidade privacidade) {
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findByPrivacidade(privacidade));
    }

    @Override @Transactional(readOnly = true)
    public List<EstacionamentoOutputDTO> findByEmpresa(UUID empresaId) {
        Empresa empresa = empresaService.findEntityById(empresaId);
        
        return estacionamentoMapper.toDtoList(estacionamentoRepository.findAllByEmpresa(empresa));
    }

    @Override @Transactional(readOnly = true)
    public EstacionamentoOutputDTO findById(UUID idEstacionamento) {
        return estacionamentoMapper.toDto(findEntityById(idEstacionamento));
    }

    @Override @Transactional(readOnly = true)
    public Estacionamento findEntityById(UUID idEstacionamento) {
       return estacionamentoRepository.findById(idEstacionamento).orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado")); 
    }

    @Override @Transactional(readOnly = true)
    public Estacionamento findEntityLocked(UUID idEstacionamento) {
        return estacionamentoRepository.findByIdForUpdate(idEstacionamento).orElseThrow(() -> new EntityNotFoundException("Estacionamento não encontrado"));
    }

}
