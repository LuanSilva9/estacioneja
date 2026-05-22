package br.com.estacioneja.modules.registro;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estacioneja.modules.registro.dto.RegistroDTO;
import br.com.estacioneja.modules.registro.dto.RegistroOutputDTO;
import br.com.estacioneja.modules.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/registro")
@RequiredArgsConstructor
public class RegistroController {
    private final RegistroService registroService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<RegistroOutputDTO> criar(@Valid @RequestBody RegistroDTO dto) {
        RegistroOutputDTO registro = registroService.create(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(registro.id()).toUri();

        return ResponseEntity.created(location).body(registro);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/historico/estacionamento/{estacionamentoId}")
    public ResponseEntity<List<RegistroOutputDTO>> verHistoricoEstacionamento(@PathVariable UUID estacionamentoId) {
        return ResponseEntity.ok(registroService.findByEstacionamento(estacionamentoId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/historico/usuario")
    public ResponseEntity<List<RegistroOutputDTO>> verHistoricoUsuario(Authentication auth) {
        Usuario usuarioAutenticado = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(registroService.findByUsuario(usuarioAutenticado.getId()));
    }
}
