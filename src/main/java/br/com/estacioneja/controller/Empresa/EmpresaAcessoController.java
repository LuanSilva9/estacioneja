package br.com.estacioneja.controller.Empresa;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.update.AcessoUpdateDto;
import br.com.estacioneja.services.Acesso.AcessoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/empresas/{empresaId}/acessos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class EmpresaAcessoController {

    private final AcessoService acessoService;

    @GetMapping
    public ResponseEntity<List<AcessoOutputDTO>> listar(@PathVariable UUID empresaId) {
        return ResponseEntity.ok(acessoService.findAccessByEmpresa(empresaId));
    }

    @GetMapping("/me")
    public ResponseEntity<AcessoOutputDTO> meuAcesso(@PathVariable UUID empresaId, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(
                acessoService.findAccessByUserAndEmpresaId(usuario, empresaId)
        );
    }

    @PostMapping
    public ResponseEntity<AcessoOutputDTO> criar(@PathVariable UUID empresaId, @Valid @RequestBody AcessoDTO dto) {
        AcessoOutputDTO acesso = acessoService.create(dto, empresaId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(acesso.id())
                .toUri();

        return ResponseEntity.created(location).body(acesso);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID empresaId, @PathVariable UUID id, @Valid @RequestBody AcessoUpdateDto dto) {
        acessoService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID empresaId, @PathVariable UUID id) {
        acessoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
