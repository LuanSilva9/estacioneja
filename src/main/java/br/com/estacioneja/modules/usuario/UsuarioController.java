package br.com.estacioneja.modules.usuario;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.estacioneja.modules.acessos.AcessoService;
import br.com.estacioneja.modules.acessos.dto.AcessoOutputDTO;
import br.com.estacioneja.modules.usuario.dto.ReadFotoPerfilDto;
import br.com.estacioneja.modules.usuario.dto.ReadUsuarioDto;
import br.com.estacioneja.modules.usuario.dto.UpdateUsuarioDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AcessoService acessoService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ReadUsuarioDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<ReadUsuarioDto> obterPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @GetMapping("/me")
    public ResponseEntity<ReadUsuarioDto> obterUsuario(Authentication auth) {
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
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody UpdateUsuarioDto dto) {
        usuarioService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/foto-perfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReadFotoPerfilDto> uploadFotoPerfil(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file,
            Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(usuarioService.uploadFotoPerfil(id, file, autenticado));
    }

    @GetMapping("/{id}/foto-perfil")
    public ResponseEntity<ReadFotoPerfilDto> obterFotoPerfil(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.getFotoPerfil(id));
    }

    @DeleteMapping("/{id}/foto-perfil")
    public ResponseEntity<Void> deletarFotoPerfil(@PathVariable UUID id, Authentication auth) {
        Usuario autenticado = (Usuario) auth.getPrincipal();
        usuarioService.deleteFotoPerfil(id, autenticado);
        return ResponseEntity.noContent().build();
    }
}
