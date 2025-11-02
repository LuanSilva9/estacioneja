package br.com.estacioneja.controller.Empresa;

import br.com.estacioneja.dto.input.EmpresaDTO;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;
import br.com.estacioneja.services.Empresa.EmpresaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<EmpresaOutputDTO> criar(@RequestBody EmpresaDTO dto) {
        EmpresaOutputDTO criado = empresaService.create(dto);
        URI location = URI.create(String.format("/api/v1/empresas/%s", criado.id()));
        return ResponseEntity.created(location).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaOutputDTO>> listarTodos() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaOutputDTO> listarEmpresa(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        empresaService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
