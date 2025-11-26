package br.com.estacioneja.controller.Empresa;

import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Empresa.EmpresaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;
    private final AcessoService acessoService;

    public EmpresaController(EmpresaService empresaService, AcessoService acessoService) {
        this.empresaService = empresaService;
        this.acessoService = acessoService;
    }

    @PostMapping
    public ResponseEntity<EmpresaOutputDTO> criar(@RequestBody EmpresaDTO dto) {
        EmpresaOutputDTO criado = empresaService.create(dto);
        URI location = URI.create(String.format("/api/v1/empresas/%s", criado.id()));
        return ResponseEntity.created(location).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaOutputDTO> listarEmpresa(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody EmpresaDTO dto) {
        empresaService.update(id, dto);
        return ResponseEntity.noContent().build();
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{empresaId}/acessos")
    public ResponseEntity<List<AcessoOutputDTO>> obterPorEmpresa(@PathVariable UUID empresaId) {
        return ResponseEntity.ok().body(acessoService.findAccessByEmpresa(empresaId));
    }

    @PostMapping("/{empresaId}/acessos")
    public ResponseEntity<AcessoOutputDTO> criarAcesso(@PathVariable UUID empresaId, @RequestBody AcessoDTO dto) {
        AcessoOutputDTO acesso = acessoService.create(dto, empresaId);

        URI location = URI.create(String.format("/api/v1/acesso/%s", acesso.id()));

        return ResponseEntity.created(location).body(acesso);
    }

    @PutMapping("/{empresaId}/acessos/{id}")
    public ResponseEntity<Void> atualizarAcesso(@PathVariable UUID empresaId, @PathVariable UUID id, @RequestBody AcessoDTO dto) {
        acessoService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{empresaId}/acessos/{id}")
    public ResponseEntity<Void> deletarAcesso(@PathVariable UUID empresaId, @PathVariable UUID id) {
        acessoService.delete(id);
        
        return ResponseEntity.noContent().build();
    }
}
