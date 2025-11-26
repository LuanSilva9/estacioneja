package br.com.estacioneja.controller.Vinculo;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.services.Vinculo.VinculoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;

    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @GetMapping
    public ResponseEntity<List<VinculoOutputDTO>> listarPorUsuario(Authentication authentication) {
        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(vinculoService.findVincleByUser(user));
    }

    @GetMapping("/estacionamento/{estacionamentoId}")
    public ResponseEntity<Void> verificaVinculo(@PathVariable UUID estacionamentoId, @RequestParam(name = "placa") String placa) {
        Boolean response = vinculoService.hasVincle(placa, estacionamentoId);

        if(!response) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
    

    @PostMapping
    public ResponseEntity<VinculoOutputDTO> criar(@RequestBody VinculoDTO dto) {
        VinculoOutputDTO vinculo = vinculoService.create(dto);

        URI location = URI.create(String.format("/api/v1/vinculos/%s", vinculo.id()));

        return ResponseEntity.created(location).body(vinculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desvincular(@PathVariable UUID id) {
        vinculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}