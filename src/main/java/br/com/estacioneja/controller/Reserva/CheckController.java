package br.com.estacioneja.controller.Reserva;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.output.VagaOutputDTO;
import br.com.estacioneja.services.Reserva.CheckService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
public class CheckController {
    @Autowired private CheckService checkService;

    @PostMapping("/checkin/{vagaId}")
    public ResponseEntity<VagaOutputDTO> checkinBase(@PathVariable UUID vagaId) throws Exception {
        return ResponseEntity.ok().body(checkService.checkinBase(vagaId));
    }
    
}
