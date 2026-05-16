package br.com.estacioneja.controller.Veiculos;

import org.springframework.security.core.Authentication;
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.VeiculoDTO;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.dto.update.VeiculoUpdateDto;
import br.com.estacioneja.services.Veiculo.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/veiculos")
@RequiredArgsConstructor
public class VeiculoController {
    private final VeiculoService veiculoService;

    
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoOutputDTO> listarPorId(@PathVariable UUID id, Authentication authentication) {
        Usuario user = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok().body(veiculoService.findById(id, user));
    }

    @GetMapping("/me")
    public ResponseEntity<List<VeiculoOutputDTO>> listarPorUsuario(Authentication authentication) {
        Usuario user = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok().body(veiculoService.findByProprietarioId(user.getId()));
    }

    
    @PostMapping
    public ResponseEntity<VeiculoOutputDTO> criar(@Valid @RequestBody VeiculoDTO dto, Authentication authentication) {
        Usuario proprietario = (Usuario) authentication.getPrincipal();
        VeiculoOutputDTO veiculo = veiculoService.create(dto, proprietario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(veiculo.id())
                .toUri();

        return ResponseEntity.created(location).body(veiculo);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody VeiculoUpdateDto dto, Authentication authentication) {
        Usuario proprietario = (Usuario) authentication.getPrincipal();
        veiculoService.update(id, dto, proprietario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id, Authentication authentication) {
        Usuario proprietario = (Usuario) authentication.getPrincipal();
        veiculoService.delete(id, proprietario);
        return ResponseEntity.noContent().build();
    }
}
