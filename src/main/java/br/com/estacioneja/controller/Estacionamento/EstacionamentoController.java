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
import br.com.estacioneja.dto.update.EstacionamentoUpdateDto;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/estacionamentos")
@RequiredArgsConstructor
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<EstacionamentoOutputDTO> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(estacionamentoService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/empresa/{id}")
    public ResponseEntity<List<EstacionamentoOutputDTO>> obterPorEmpresa(@PathVariable UUID id) {
        return ResponseEntity.ok(estacionamentoService.findByEmpresa(id));
    }

    @GetMapping("/privacidade/{privacidade}")
    public ResponseEntity<List<EstacionamentoOutputDTO>> obterPorPrivacidade(@PathVariable Privacidade privacidade) {
        return ResponseEntity.ok(estacionamentoService.findByPrivacidade(privacidade));
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EstacionamentoOutputDTO> criar(@Valid @RequestBody EstacionamentoDTO dto) {
        EstacionamentoOutputDTO criado = estacionamentoService.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody EstacionamentoUpdateDto dto) {
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
