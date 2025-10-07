package br.com.estacioneja.controller.Filial;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.AcessoDTO;
import br.com.estacioneja.dto.input.FilialDTO;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.dto.output.FilialOutputDTO;
import br.com.estacioneja.services.Acesso.AcessoService;
import br.com.estacioneja.services.Filial.FilialService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/filiais")
public class FilialController {
    private final FilialService filialService;
    private final AcessoService acessoService;

    public FilialController(FilialService filialService, AcessoService acessoService) {
        this.filialService = filialService;
        this.acessoService = acessoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilialOutputDTO> listarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(filialService.findById(id));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<FilialOutputDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(filialService.findAllByEmpresaId(empresaId));
    }
    

    @PostMapping
    public ResponseEntity<FilialOutputDTO> criar(@RequestBody FilialDTO dto) {
        FilialOutputDTO criado = filialService.create(dto);
        URI location = URI.create(String.format("/api/v1/filiais/%s", criado.id()));

        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody FilialDTO dto) {
        filialService.update(id, dto);

        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        filialService.delete(id);
        return ResponseEntity.noContent().build();
    }
    

    // Acessos por filial

    @GetMapping("/{filialId}/acessos")
    public ResponseEntity<List<AcessoOutputDTO>> obterPorEmpresa(@PathVariable UUID filialId) {
        return ResponseEntity.ok().body(acessoService.findAccessByFilial(filialId));
    }

    @PostMapping("/{filialId}/acessos")
    public ResponseEntity<AcessoOutputDTO> criarAcesso(@PathVariable UUID filialId, @RequestBody AcessoDTO dto) {
        AcessoOutputDTO acesso = acessoService.createFilial(dto, filialId);

        URI location = URI.create(String.format("/api/v1/acesso/%s", acesso.id()));

        return ResponseEntity.created(location).body(acesso);
    }

    @PutMapping("/{filialId}/acessos/{id}")
    public ResponseEntity<Void> atualizarTipo(@PathVariable UUID filialId, @PathVariable UUID id, @RequestBody AcessoDTO dto) {
        acessoService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{filialId}/acessos/{id}")
    public ResponseEntity<Void> atualizarTipo(@PathVariable UUID filialId, @PathVariable UUID id) {
        acessoService.delete(id);
        
        return ResponseEntity.noContent().build();
    }
    
    
}
