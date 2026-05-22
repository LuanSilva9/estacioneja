package br.com.estacioneja.modules.vinculo;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estacioneja.modules.usuario.Usuario;
import br.com.estacioneja.modules.vinculo.dto.VinculoCardUsuarioDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoConsultaGuaritaDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoDTO;
import br.com.estacioneja.modules.vinculo.dto.VinculoLinhaAdminDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vinculos")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class VinculoController {

    private final VinculoService vinculoService;

    @GetMapping
    public ResponseEntity<List<VinculoCardUsuarioDTO>> listarPorUsuario(Authentication authentication) {
        Usuario user = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(vinculoService.findCardUsuarioByUsuario(user));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<VinculoLinhaAdminDTO>> listarPorEmpresa(@PathVariable UUID empresaId) {
        return ResponseEntity.ok(vinculoService.findLinhaAdminByEmpresa(empresaId));
    }

    @GetMapping("/estacionamento/{estacionamentoId}")
    public ResponseEntity<VinculoConsultaGuaritaDTO> verificaVinculoERetorna(@PathVariable UUID estacionamentoId, @RequestParam String placa) {
        return ResponseEntity.ok(vinculoService.findConsultaGuarita(placa, estacionamentoId));
    }

    @PostMapping
    public ResponseEntity<VinculoLinhaAdminDTO> criar(@Valid @RequestBody VinculoDTO dto) {
        VinculoLinhaAdminDTO vinculo = vinculoService.create(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vinculo.id()).toUri();

        return ResponseEntity.created(location).body(vinculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desvincular(@PathVariable UUID id) {
        vinculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
