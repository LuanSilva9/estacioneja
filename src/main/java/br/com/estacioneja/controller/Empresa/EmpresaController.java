package br.com.estacioneja.controller.Empresa;

import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.modules.usuario.dto.ReadFotoPerfilDto;
import br.com.estacioneja.dto.update.EmpresaUpdateDto;
import br.com.estacioneja.services.Empresa.EmpresaOrquestradorService;
import br.com.estacioneja.services.Empresa.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/empresas")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;
    private final EmpresaOrquestradorService empresaOrquestradorService;


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EmpresaOutputDTO> listarEmpresa(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }


    @PostMapping
    @PreAuthorize("hasRole('INTERNAL_SERVICE')")
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody EmpresaUpdateDto dto) {
        empresaService.update(id, dto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/{id}/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReadFotoPerfilDto> uploadLogo(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file,
            Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(empresaService.uploadLogo(id, file, autenticado));
    }

    @GetMapping("/{id}/logo")
    public ResponseEntity<ReadFotoPerfilDto> obterLogo(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.getLogo(id));
    }

    @DeleteMapping("/{id}/logo")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletarLogo(@PathVariable UUID id, Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        empresaService.deleteLogo(id, autenticado);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/{id}/banner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReadFotoPerfilDto> uploadBanner(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file,
            Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(empresaService.uploadBanner(id, file, autenticado));
    }

    @GetMapping("/{id}/banner")
    public ResponseEntity<ReadFotoPerfilDto> obterBanner(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.getBanner(id));
    }

    @DeleteMapping("/{id}/banner")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletarBanner(@PathVariable UUID id, Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        empresaService.deleteBanner(id, autenticado);
        return ResponseEntity.noContent().build();
    }
}
