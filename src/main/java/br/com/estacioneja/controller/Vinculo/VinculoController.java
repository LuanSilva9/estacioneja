package br.com.estacioneja.controller.Vinculo;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.VinculoDTO;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.services.Vinculo.VinculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/vinculos")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class VinculoController {

    private final VinculoService vinculoService;

    
    @GetMapping
    public ResponseEntity<List<VinculoOutputDTO>> listarPorUsuario(Authentication authentication) {
        Usuario user = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(vinculoService.findVincleByUser(user));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<VinculoOutputDTO>> listarPorEmpresa(@PathVariable UUID empresaId) {
        List<VinculoOutputDTO> vinculos = vinculoService.findVincleByEmpresa(empresaId);
        
        return ResponseEntity.ok(vinculos);
    }
    

   
    @GetMapping("/estacionamento/{estacionamentoId}")
    public ResponseEntity<VinculoOutputDTO> verificaVinculoERetorna(@PathVariable UUID estacionamentoId, @RequestParam String placa) {
        return ResponseEntity.ok(vinculoService.findVincleByPlacaAndEstacionamentoId(placa, estacionamentoId));
    }

    @PostMapping
    public ResponseEntity<VinculoOutputDTO> criar(@Valid @RequestBody VinculoDTO dto) {
        VinculoOutputDTO vinculo = vinculoService.create(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vinculo.id()).toUri();

        return ResponseEntity.created(location).body(vinculo);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desvincular(@PathVariable UUID id) {
        vinculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
