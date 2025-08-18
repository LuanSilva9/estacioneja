package br.com.estacioneja.services.Reserva;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.dto.output.VagaOutputDTO;
import br.com.estacioneja.infra.config.mapper.VagaMapper;

@Service
public class CheckService {
    public final ReservaService reservaService;
    public final VagaMapper vagaMapper;

    public CheckService(ReservaService reservaService, VagaMapper vagaMapper) {
        this.reservaService = reservaService;
        this.vagaMapper = vagaMapper;
    }

    public VagaOutputDTO checkinBase(UUID vagaId) {
        return null;
    }
}
