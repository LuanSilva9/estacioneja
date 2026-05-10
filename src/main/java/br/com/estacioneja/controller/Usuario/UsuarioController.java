package br.com.estacioneja.controller.Usuario;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.output.URLImagemOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.dto.update.UsuarioUpdateDto;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AcessoService acessoService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutputDTO> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioOutputDTO> obterPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioOutputDTO> obterUsuario(Authentication auth) {
        Usuario usuario = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(usuarioService.findById(usuario.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/my-workspaces")
    public ResponseEntity<List<AcessoOutputDTO>> obterWorkspaces(Authentication auth) {
        Usuario usuario = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(acessoService.findAccessByUser(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioUpdateDto dto) {
        usuarioService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/foto-perfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<URLImagemOutputDTO> uploadFotoPerfil(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file,
            Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(usuarioService.uploadFotoPerfil(id, file, autenticado));
    }

    @GetMapping("/{id}/foto-perfil")
    public ResponseEntity<URLImagemOutputDTO> obterFotoPerfil(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.getFotoPerfil(id));
    }

    @DeleteMapping("/{id}/foto-perfil")
    public ResponseEntity<Void> deletarFotoPerfil(@PathVariable UUID id, Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        usuarioService.deleteFotoPerfil(id, autenticado);
        return ResponseEntity.noContent().build();
    }
}
