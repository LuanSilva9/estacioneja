package br.com.estacioneja.services.Reserva;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacioneja.dto.output.VagaOutputDTO;
import br.com.estacioneja.infra.config.mapper.VagaMapper;

@Service
public class CheckService {
    @Autowired ReservaService reservaService;

    @Autowired VagaMapper vagaMapper;

    public VagaOutputDTO checkinBase(UUID vagaId) throws Exception {
        return null;
    }
}
