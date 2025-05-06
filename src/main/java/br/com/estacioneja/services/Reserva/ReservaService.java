package br.com.estacioneja.services.Reserva;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Reserva.Reserva;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.domain.repository.Reserva.ReservaRepository;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.domain.repository.Vaga.VagaRepository;
import br.com.estacioneja.dto.ReservaDTO;
import jakarta.transaction.Transactional;

@Service 
public class ReservaService {
    @Autowired private ReservaRepository reservaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private VagaRepository vagaRepository;

    @Transactional
    public List<Reserva> listaReservas() {
        return reservaRepository.findAll();
    }

    @Transactional
    public List<Reserva> listaReservasPorEstacionamento(UUID estacionamentoId) {
        return reservaRepository.findByEstacionamentoId(estacionamentoId);
    }

    @Transactional
    public Reserva criarReserva(ReservaDTO dto) throws Exception {
        if(!isVacancyAvaliable(dto.vagaId())) {
            throw new Exception("Vaga já está reservada!");
        }
        if(!isTimeAvaliable(dto)) {
            throw new Exception("Você já possui uma reserva para esse horario");
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new Exception("Usuario não encontrado!"));
        Vaga vaga = vagaRepository.findById(dto.vagaId()).orElseThrow(() -> new Exception("Vaga não encontrada!"));

        Reserva newReserva = new Reserva(dto, usuario, vaga);

        return reservaRepository.save(newReserva);
    }

    public Boolean isVacancyAvaliable(UUID vagaId) {
        return reservaRepository.countByAvaliable(vagaId) == 1;
    }

    public Boolean isTimeAvaliable(ReservaDTO dto) {
        return reservaRepository.countByTimeConflicts(dto.usuarioId(), dto.horarioEntrada(), dto.horarioSaida()) == 0;
    }
}
