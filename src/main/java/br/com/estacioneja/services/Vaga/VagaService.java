package br.com.estacioneja.services.Vaga;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.domain.repository.Estacionamento.EstacionamentoRepository;
import br.com.estacioneja.domain.repository.Vaga.VagaRepository;
import br.com.estacioneja.dto.i.VagaDTO;
import jakarta.transaction.Transactional;

@Service
public class VagaService {
    @Autowired private VagaRepository vagaRepository;
    @Autowired private EstacionamentoRepository estacionamentoRepository;

    @Transactional
    public List<Vaga> listarVagas() {
        return vagaRepository.findAll();
    }

    @Transactional
    public List<Vaga> listarVagasPorEstacionamento(UUID estacionamentoId) throws Exception {
        Estacionamento estacionamento = estacionamentoRepository.findById(estacionamentoId).orElseThrow(() -> new Exception("Estacionamento não encontrado"));

        return vagaRepository.findAllByEstacionamento(estacionamento);
    }

    @Transactional
    public Vaga criarVaga(VagaDTO dto) throws Exception {
        Estacionamento estacionamento = estacionamentoRepository.findById(dto.estacionamentoId()).orElseThrow(() -> new Exception("Estacionamento não encontrado"));

        Vaga newVaga = new Vaga(dto, estacionamento);

        estacionamento.setCapacidadeTotal(estacionamento.getCapacidadeTotal() + 1);
        estacionamento.setVagasDisponiveis(estacionamento.getVagasDisponiveis() + 1);
        
        estacionamentoRepository.save(estacionamento);

        return vagaRepository.save(newVaga);
    }

    @Transactional
    public Vaga criarVaga(Estacionamento estacionamento, String slug) throws Exception {
        Vaga newVaga = new Vaga(estacionamento, slug);

        return vagaRepository.save(newVaga);
    }

    @Transactional
    public Vaga deletarVaga(UUID id) throws Exception {
        Vaga vaga = vagaRepository.findById(id).orElseThrow(() -> new Exception("Vaga não encontrada!"));

        Estacionamento estacionamento = vaga.getEstacionamento();

        estacionamento.setVagasDisponiveis(estacionamento.getVagasDisponiveis() - 1);

        estacionamentoRepository.save(estacionamento);

        vagaRepository.delete(vaga);

        return vaga;
    }

    @Transactional
    public int deletarVagasPorEstacionamento(UUID id) throws Exception {
        Estacionamento estacionamento = estacionamentoRepository.findById(id).orElseThrow(() -> new Exception("Estacionamento não encontrado!"));

        int vagasRemovidas = vagaRepository.countByEstacionamento(estacionamento);

        estacionamento.setVagasDisponiveis(0L);
        estacionamentoRepository.save(estacionamento);

        vagaRepository.deleteAllByEstacionamento(estacionamento);

        return vagasRemovidas;
    }

}
