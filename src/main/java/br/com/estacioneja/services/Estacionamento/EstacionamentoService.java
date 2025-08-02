package br.com.estacioneja.services.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Vaga.StatusVaga;
import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.domain.repository.Vaga.VagaRepository;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.exceptions.custom.ParkNotFoundException;
import br.com.estacioneja.infra.config.mapper.EstacionamentoMapper;
import br.com.estacioneja.services.Empresa.EmpresaService;
import br.com.estacioneja.usecases.interfaces.IEstacionamento;
import jakarta.transaction.Transactional;

@Service
public class EstacionamentoService implements IEstacionamento {
    private final EstacionamentoRepository estacionamentoRepository;
    private final EmpresaService empresaService;
    private final VagaRepository vagaRepository;
    private final EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoService(EstacionamentoRepository estacionamentoRepository, EmpresaService empresaService, VagaRepository vagaRepository, EstacionamentoMapper estacionamentoMapper) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.empresaService = empresaService;
        this.vagaRepository = vagaRepository;
        this.estacionamentoMapper = estacionamentoMapper;
    }

    @Override @Transactional
    public EstacionamentoOutputDTO create(EstacionamentoDTO dto) {
        Empresa empresa = empresaService.findEntityById(dto.empresaId());

        Estacionamento newEstacionamento = new Estacionamento(dto, empresa);
        Estacionamento saved = estacionamentoRepository.save(newEstacionamento);

        for (long i = 0L; i < dto.capacidade(); i++) {
            String slug = empresa.getPrefixo() + "-" + saved.getPrefixo() + "-" + i;
            Vaga newVaga = new Vaga(saved, slug);
            vagaRepository.save(newVaga);
        }        

        return estacionamentoMapper.toDto(saved);
    }
    
    @Override @Transactional
    public EstacionamentoOutputDTO update(UUID id, EstacionamentoDTO dto) {
        Empresa empresa = empresaService.findEntityById(dto.empresaId());

        Estacionamento estacionamento = findEntityById(id);

        estacionamento.setEmpresa(empresa);
        estacionamento.setStatusEstacionamento(dto.statusEstacionamento());

        return estacionamentoMapper.toDto(estacionamentoRepository.save(estacionamento));
    }

    @Override @Transactional
    public void delete(UUID id) {
        Estacionamento estacionamento = findEntityById(id);

        estacionamentoRepository.delete(estacionamento);
    }
    
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

    @Override
    public Long getAvaliableVacancies(UUID idEstacionamento) {
        return vagaRepository.countByEstacionamentoIdAndStatusVaga(idEstacionamento, StatusVaga.LIVRE);
    }

    @Override
    public Long getTotalVacancies(UUID idEstacionamento) {
        return vagaRepository.countByEstacionamentoId(idEstacionamento);
    }

}
