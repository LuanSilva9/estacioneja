package br.com.estacioneja.controller.Empresa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.i.EmpresaDTO;
import br.com.estacioneja.dto.o.EmpresaOutputDTO;
import br.com.estacioneja.services.Empresa.EmpresaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @PostMapping("criar")
    public ResponseEntity<String> createCompany(@RequestBody EmpresaDTO dto) {
        try {
            empresaService.createCompany(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa criada com sucesso!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar criar empresa!\n" + e);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<EmpresaOutputDTO>> listCompany() {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.listCompany());
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        try {
            empresaService.updateCompany(id, dto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa Atualizada com sucesso!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar atualizar empresa!\n" + e);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        try {
            empresaService.deleteCompany(id);

            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa deletada com sucesso!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar deletar empresa!\n" + e);
        }
    }
}
