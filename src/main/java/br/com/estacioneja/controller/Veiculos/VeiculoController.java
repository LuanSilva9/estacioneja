package br.com.estacioneja.controller.Veiculos;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.services.Veiculo.VeiculoService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {
    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoOutputDTO> listarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(veiculoService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<VeiculoOutputDTO> criar(@RequestBody VeiculoDTO dto) {
        VeiculoOutputDTO veiculo = veiculoService.create(dto);

        URI location = URI.create(String.format("/api/v1/usuarios/veiculos/%s", veiculo.id()));

        return ResponseEntity.created(location).body(veiculo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody VeiculoDTO dto) {
        veiculoService.update(id, dto);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
