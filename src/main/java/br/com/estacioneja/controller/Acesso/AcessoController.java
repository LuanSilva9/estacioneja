package br.com.estacioneja.controller.Acesso;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping; 

@RestController
@RequestMapping("/api/v1/acessos")
public class AcessoController {

    private final AcessoService acessoService;

    public AcessoController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcessoOutputDTO> obter(@PathVariable UUID id) {
        return ResponseEntity.ok(acessoService.findById(id));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<AcessoOutputDTO>> obterPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok().body(acessoService.findAccessByCompany(empresaId));
    }
    

    @PostMapping
    public ResponseEntity<AcessoOutputDTO> criar(@RequestBody AcessoDTO dto) {
        AcessoOutputDTO acesso = acessoService.create(dto);

        URI location = URI.create(String.format("/api/v1/acesso/%s", acesso.id()));

        return ResponseEntity.created(location).body(acesso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarTipo(@PathVariable UUID id, @RequestBody AcessoDTO dto) {
        acessoService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        acessoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}