package br.com.estacioneja.controller.Registro;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.RegistroDTO;
import br.com.estacioneja.dto.output.RegistroOutputDTO;
import br.com.estacioneja.services.Registro.RegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/registro")
@RequiredArgsConstructor
public class RegistroController {
    private final RegistroService registroService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RegistroOutputDTO> criar(@Valid @RequestBody RegistroDTO dto) {
        RegistroOutputDTO registro = registroService.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registro.id())
                .toUri();

        return ResponseEntity.created(location).body(registro);
    }

    @GetMapping("/historico/estacionamento/{estacionamentoId}")
    public ResponseEntity<List<RegistroOutputDTO>> verHistoricoEstacionamento(@PathVariable UUID estacionamentoId) {
        List<RegistroOutputDTO> historicoEstacionamento = registroService.findByEstacionamento(estacionamentoId);

        return ResponseEntity.ok().body(historicoEstacionamento);
    }

    @GetMapping("/historico/usuario")
    public ResponseEntity<List<RegistroOutputDTO>> verHistoricoUsuario(Authentication auth) {
        Usuario usuarioAutenticado = (Usuario) auth.getPrincipal();
        List<RegistroOutputDTO> historicoUsuario = registroService.findByUsuario(usuarioAutenticado.getId());

        return ResponseEntity.ok().body(historicoUsuario);
    }
    

}
