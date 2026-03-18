package br.com.estacioneja.controller.Usuario;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.dto.update.UsuarioUpdateDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Usuario.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AcessoService acessoService;

    public UsuarioController(UsuarioService usuarioService, AcessoService acessoService) {
        this.usuarioService = usuarioService;
        this.acessoService = acessoService;
    }

    @PreAuthorize("authenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutputDTO> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PreAuthorize("authenticated()")
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
    

    @PostMapping
    public ResponseEntity<UsuarioOutputDTO> criar(@RequestBody UsuarioDTO dto) {
        UsuarioOutputDTO criado = usuarioService.create(dto);
        URI location = URI.create(String.format("/api/v1/usuarios/%s", criado.id()));
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody UsuarioUpdateDTO dto) {
        usuarioService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.delete(id);
        
        return ResponseEntity.noContent().build();
    }
}