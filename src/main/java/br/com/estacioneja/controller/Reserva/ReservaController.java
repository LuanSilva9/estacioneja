package br.com.estacioneja.controller.Reserva;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Reserva.Reserva;
import br.com.estacioneja.dto.i.ReservaDTO;
import br.com.estacioneja.services.Reserva.ReservaService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/reserva")
public class ReservaController {
    @Autowired private ReservaService reservaService;
    
    @GetMapping("listar")
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.listaReservas());
    }
    
    @GetMapping("listar-por-estacionamento/{estacionamentoId}")
    public ResponseEntity<List<Reserva>> listarReservasPorEstacionamento(@PathVariable UUID estacionamentoId) {
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.listaReservasPorEstacionamento(estacionamentoId));
    }
    
    @PostMapping("criar")
    public ResponseEntity<String> criarReserva(@RequestBody ReservaDTO dto) {
        try {
            reservaService.criarReserva(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva Criada com Sucesso");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel criar a reserva,\n" + e);
        }
    }
    
    @DeleteMapping("delt/{id}")
    public ResponseEntity<String> deleteReserva(@PathVariable UUID id) {
        try {
            reservaService.deleteReserva(id);

            return ResponseEntity.status(HttpStatus.OK).body("Reserva Deletada com Sucesso");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel deletar a reserva, \n" + e);
        }
    }


}
