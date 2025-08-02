package br.com.estacioneja.controller.Vinculo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.services.Vinculo.VinculoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;

    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<VinculoOutputDTO>> listarPorUsuario(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(vinculoService.findVincleByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody VinculoDTO dto) {
        vinculoService.create(dto);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desvincular(@PathVariable Long id) {
        vinculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}