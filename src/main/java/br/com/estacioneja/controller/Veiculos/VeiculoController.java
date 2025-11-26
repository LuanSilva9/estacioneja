package br.com.estacioneja.controller.Veiculos;

import org.springframework.security.core.Authentication;
import java.net.URI;
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
import br.com.estacioneja.services.Veiculo.VeiculoService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {
    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoOutputDTO> listarPorId(@PathVariable UUID id, Authentication authentication) {
        Usuario user = (Usuario) authentication.getPrincipal();
        
        return ResponseEntity.ok().body(veiculoService.findById(id, user));
    }
    
    @PostMapping
    public ResponseEntity<VeiculoOutputDTO> criar(@RequestBody VeiculoDTO dto, Authentication authentication) {
        Usuario proprietario = (Usuario) authentication.getPrincipal();
        VeiculoOutputDTO veiculo = veiculoService.create(dto, proprietario);

        URI location = URI.create(String.format("/api/v1/usuarios/veiculos/%s", veiculo.id()));

        return ResponseEntity.created(location).body(veiculo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody VeiculoDTO dto, Authentication authentication) {
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
