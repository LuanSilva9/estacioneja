package br.com.estacioneja.services.Reserva;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Reserva.Reserva;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.domain.repository.Reserva.ReservaRepository;
import br.com.estacioneja.dto.input.ReservaDTO;
import br.com.estacioneja.exceptions.custom.ParkIsFullException;
import br.com.estacioneja.exceptions.custom.TimeIsNotAvailableException;
import br.com.estacioneja.exceptions.custom.VacancyIsNotAvailableException;
import br.com.estacioneja.exceptions.custom.VeicleNotFoundException;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import br.com.estacioneja.services.Vaga.VagaService;
import br.com.estacioneja.services.Veiculo.VeiculoService;
import jakarta.transaction.Transactional;

@Service 
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final UsuarioService usuarioService;
    private final VagaService vagaService;
    private final EstacionamentoService estacionamentoService;
    private final VeiculoService veiculoService;

    public ReservaService(ReservaRepository reservaRepository, UsuarioService usuarioService, VagaService vagaService, EstacionamentoService estacionamentoService, VeiculoService veiculoService) {
        this.reservaRepository = reservaRepository;
        this.usuarioService = usuarioService;
        this.vagaService = vagaService;
        this.estacionamentoService = estacionamentoService;
        this.veiculoService = veiculoService;
    }

    @Transactional
    public List<Reserva> listaReservas() {
        return reservaRepository.findAll();
    }

    @Transactional
    public List<Reserva> listaReservasPorEstacionamento(UUID estacionamentoId) {
        return reservaRepository.findByEstacionamentoId(estacionamentoId);
    }

    @Transactional
    public Reserva realizarReserva(ReservaDTO dto) {
        Usuario usuario = usuarioService.findEntityById(dto.usuarioId());
        Vaga vaga = vagaService.findEntityById(dto.vagaId());
        Veiculo veiculo = veiculoService.getById(dto.veiculoId());
        Estacionamento estacionamento = vaga.getEstacionamento();

        validarDisponibilidadeVaga(dto.vagaId());
        validarDisponibilidadeHorario(dto);
        validarEstacionamento(estacionamento);
        validarProprietarioVeiculo(veiculo, usuario);

        vagaService.toSchedule(vaga);
        return reservaRepository.save(new Reserva(dto, usuario, vaga));
    }

    @Transactional
    public Reserva deleteReserva(UUID id) throws Exception {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new Exception("Reserva não encontrada."));
        
        reservaRepository.delete(reserva);
        
        return reserva;
    }
    
    private Boolean isVacancyAvaliable(UUID vagaId) {
        return reservaRepository.countByAvaliable(vagaId) == 1;
    }
    
    private Boolean isTimeAvaliable(ReservaDTO dto) {
        return reservaRepository.countByTimeConflicts(dto.usuarioId(), dto.horarioEntrada(), dto.horarioSaida()) == 0;
    }
    
    private void validarDisponibilidadeVaga(UUID vagaId) {
        if (!isVacancyAvaliable(vagaId)) {
            throw new VacancyIsNotAvailableException();
        }
    }

    private void validarDisponibilidadeHorario(ReservaDTO dto) {
        if (!isTimeAvaliable(dto)) {
            throw new TimeIsNotAvailableException();
        }
    }

    private void validarEstacionamento(Estacionamento estacionamento) {
        if (estacionamentoService.getAvaliableVacancies(estacionamento.getId()) == 0) {
            throw new ParkIsFullException();
        }
    }

    private void validarProprietarioVeiculo(Veiculo veiculo, Usuario usuario) {
        if (!veiculo.getProprietario().getId().equals(usuario.getId())) {
            throw new VeicleNotFoundException();
        }
    }
}
