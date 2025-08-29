package br.com.estacioneja.controller.Acesso;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/acessos")
public class AcessoController {

    private final AcessoService acessoService;

    public AcessoController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcessoOutputDTO> obter(@PathVariable UUID id) {
        return ResponseEntity.ok(acessoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody AcessoDTO dto) {
        acessoService.create(dto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarTipo(@PathVariable UUID id, @RequestBody AcessoDTO dto) {
        acessoService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        acessoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}