package br.com.estacioneja.controller.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.enums.Privacidade;
import br.com.estacioneja.dto.input.EstacionamentoDTO;
import br.com.estacioneja.dto.output.EstacionamentoOutputDTO;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;

    public EstacionamentoController(EstacionamentoService estacionamentoService) {
        this.estacionamentoService = estacionamentoService;
    }
    
    @PreAuthorize("authenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<EstacionamentoOutputDTO> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(estacionamentoService.findById(id));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/empresa/{id}")
    public ResponseEntity<List<EstacionamentoOutputDTO>> obterPorEmpresa(@PathVariable UUID id) {
        return ResponseEntity.ok(estacionamentoService.findByEmpresa(id));
    }


    @PreAuthorize("authenticated()")
    @GetMapping("/privacidade/{privacidade}")
    public ResponseEntity<List<EstacionamentoOutputDTO>> obterPorId(@PathVariable(name = "privacidade") Privacidade privacidade) {
        return ResponseEntity.ok(estacionamentoService.findByPrivacidade(privacidade));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EstacionamentoOutputDTO> criar(@RequestBody EstacionamentoDTO dto) {
        EstacionamentoOutputDTO criado = estacionamentoService.create(dto);
        URI location = URI.create(String.format("/api/v1/estacionamentos/%s", criado.id()));
        return ResponseEntity.created(location).body(criado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody EstacionamentoDTO dto) {
        estacionamentoService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        estacionamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}