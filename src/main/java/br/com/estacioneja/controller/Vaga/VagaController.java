package br.com.estacioneja.controller.Vaga;

import br.com.estacioneja.dto.input.VagaDTO;
import br.com.estacioneja.dto.output.VagaOutputDTO;
import br.com.estacioneja.services.Vaga.VagaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaOutputDTO> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(vagaService.findById(id));
    }

    @GetMapping("/estacionamento/{estacionamentoId}")
    public ResponseEntity<List<VagaOutputDTO>> listarPorEstacionamento(@PathVariable UUID estacionamentoId) {
        return ResponseEntity.ok(vagaService.findAllByParking(estacionamentoId));
    }

    @PostMapping
    public ResponseEntity<VagaOutputDTO> criar(@RequestBody VagaDTO dto) {
        VagaOutputDTO criado = vagaService.create(dto);
        URI location = URI.create(String.format("/api/v1/vagas/%s", criado.id()));
        return ResponseEntity.created(location).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        vagaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/estacionamento/{estacionamentoId}")
    public ResponseEntity<Void> deletarPorEstacionamento(@PathVariable UUID estacionamentoId) {
        vagaService.deleteAllByParking(estacionamentoId);
        return ResponseEntity.noContent().build();
    }
}