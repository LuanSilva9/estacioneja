package br.com.estacioneja.controller.Empresa;

import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.dto.update.EmpresaUpdateDto;
import br.com.estacioneja.services.Empresa.EmpresaOrquestradorService;
import br.com.estacioneja.services.Empresa.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/empresas")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;
    private final EmpresaOrquestradorService empresaOrquestradorService;

  
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaOutputDTO> listarEmpresa(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }

    
    @PostMapping
    public ResponseEntity<EmpresaOutputDTO> criar(@Valid @RequestBody EmpresaDTO dto) {
        EmpresaOutputDTO criado = empresaOrquestradorService.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();

        return ResponseEntity.created(location).body(criado);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody EmpresaUpdateDto dto) {
        empresaService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
