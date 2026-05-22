package br.com.estacioneja.modules.equipamento;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estacioneja.modules.equipamento.dto.EquipamentoDTO;
import br.com.estacioneja.modules.equipamento.dto.EquipamentoOutputDTO;
import br.com.estacioneja.modules.equipamento.dto.EquipamentoUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/equipamentos")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class EquipamentoController {
    private final EquipamentoService equipamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoOutputDTO> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(equipamentoService.findById(id));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<EquipamentoOutputDTO>> listarPorEmpresa(@PathVariable UUID empresaId) {
        return ResponseEntity.ok(equipamentoService.findByEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<EquipamentoOutputDTO> criar(@Valid @RequestBody EquipamentoDTO dto) {
        EquipamentoOutputDTO criado = equipamentoService.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody EquipamentoUpdateDto dto) {
        equipamentoService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        equipamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
