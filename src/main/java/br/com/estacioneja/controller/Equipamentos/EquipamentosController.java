package br.com.estacioneja.controller.Equipamentos;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.EquipamentoDTO;
import br.com.estacioneja.dto.output.EquipamentoOutputDTO;
import br.com.estacioneja.services.Equipamento.EquipamentoService;

@RestController
@RequestMapping("/api/v1/equipamentos")
public class EquipamentosController {
    private final EquipamentoService equipamentoService;

    public EquipamentosController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoOutputDTO> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(equipamentoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EquipamentoOutputDTO> criar(@RequestBody EquipamentoDTO dto) {
        EquipamentoOutputDTO criado = equipamentoService.create(dto);
        URI location = URI.create(String.format("/api/v1/equipamentos/%s", criado.id()));
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody EquipamentoDTO dto) {
        equipamentoService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        equipamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}