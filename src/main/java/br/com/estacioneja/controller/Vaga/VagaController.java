package br.com.estacioneja.controller.Vaga;

import br.com.estacioneja.domain.model.Vaga.Vaga;
import br.com.estacioneja.dto.VagaDTO;
import br.com.estacioneja.services.Vaga.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("vagas")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @GetMapping("listar")
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> vagas = vagaService.listarVagas();
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("listar/estacionamento/{estacionamentoId}")
    public ResponseEntity<List<Vaga>> listarVagasPorEstacionamento(@PathVariable UUID estacionamentoId) {
        try {
            List<Vaga> vagas = vagaService.listarVagasPorEstacionamento(estacionamentoId);
            return ResponseEntity.ok(vagas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("criar")
    public ResponseEntity<Vaga> criarVaga(@RequestBody VagaDTO dto) {
        try {
            Vaga vaga = vagaService.criarVaga(dto);
            return ResponseEntity.ok(vaga);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deletarVaga(@PathVariable UUID id) {
        try {
            vagaService.deletarVaga(id);

            return ResponseEntity.status(HttpStatus.OK).body("Vaga Deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar vaga, \n" + e);
        }
    }

    @DeleteMapping("deletar-por-estacionamento/{estacionamentoId}")
    public ResponseEntity<String> deletarVagasPorEstacionamento(@PathVariable UUID estacionamentoId) {
        try {
            int counter = vagaService.deletarVagasPorEstacionamento(estacionamentoId);

            return ResponseEntity.status(HttpStatus.OK).body(counter + " Vagas Deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar vaga, \n" + e);
        }
    }
}