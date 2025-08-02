package br.com.estacioneja.services.Vaga;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Vaga.StatusVaga;
import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.domain.repository.Vaga.VagaRepository;
import br.com.estacioneja.dto.input.VagaDTO;
import br.com.estacioneja.dto.output.VagaOutputDTO;
import br.com.estacioneja.exceptions.custom.VacancyNotFoundException;
import br.com.estacioneja.infra.config.mapper.VagaMapper;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.usecases.interfaces.IVaga;
import jakarta.transaction.Transactional;

@Service
public class VagaService implements IVaga {
    private final VagaRepository vagaRepository;
    private final EstacionamentoService estacionamentoService;
    private final VagaMapper vagaMapper;

    

    public VagaService(VagaRepository vagaRepository, EstacionamentoService estacionamentoService, VagaMapper vagaMapper) {
        this.vagaRepository = vagaRepository;
        this.estacionamentoService = estacionamentoService;
        this.vagaMapper = vagaMapper;
    }

    @Override @Transactional
    public VagaOutputDTO create(VagaDTO dto) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(dto.estacionamentoId());

        Vaga newVaga = new Vaga(dto, estacionamento);

        return save(newVaga);
    }

    @Override @Transactional
    public VagaOutputDTO update(UUID id, VagaDTO dto) {
        Vaga vaga = findEntityById(id);

        vaga.setSlug(dto.slug());
        vaga.setTipoVaga(dto.tipoVaga());
        
        return vagaMapper.toDto(vagaRepository.save(vaga));
    }

    @Override @Transactional
    public void delete(UUID id)  {
        Vaga vaga = findEntityById(id);

        vagaRepository.delete(vaga);
    }

    @Override @Transactional
    public void deleteAllByParking(UUID estacionamentoId) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(estacionamentoId);

        vagaRepository.deleteAllByEstacionamento(estacionamento);
    }

    @Transactional
    public VagaOutputDTO save(Vaga vaga) {
        return vagaMapper.toDto(vagaRepository.save(vaga));
    }

    @Override @Transactional
    public VagaOutputDTO toFree(Vaga vaga) {
        vaga.setStatusVaga(StatusVaga.LIVRE);

        return vagaMapper.toDto(vagaRepository.save(vaga));
    }

    @Override @Transactional
    public VagaOutputDTO toSchedule(Vaga vaga) {
        vaga.setStatusVaga(StatusVaga.AGENDADA);

        return vagaMapper.toDto(vagaRepository.save(vaga));
    }

    @Override @Transactional
    public VagaOutputDTO toOccupy(Vaga vaga) {
        vaga.setStatusVaga(StatusVaga.OCUPADA);

        return vagaMapper.toDto(vagaRepository.save(vaga));
    }
    
    @Override @Transactional
    public VagaOutputDTO findById(UUID vagaId) {
        return vagaMapper.toDto(findEntityById(vagaId));
    }

    @Override @Transactional
    public Vaga findEntityById(UUID vagaId) {
        return vagaRepository.findById(vagaId).orElseThrow(VacancyNotFoundException::new);
    }


    @Override @Transactional
    public List<VagaOutputDTO> findAllByParking(UUID idEstacionamento) {
        Estacionamento estacionamento = estacionamentoService.findEntityById(idEstacionamento);

        return vagaMapper.toDtoList(vagaRepository.findAllByEstacionamento(estacionamento));
    }

}
