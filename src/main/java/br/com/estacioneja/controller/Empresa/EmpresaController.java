package br.com.estacioneja.controller.Empresa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.dto.EmpresaDTO;
import br.com.estacioneja.services.Empresa.EmpresaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @PostMapping("criar")
    public ResponseEntity<String> postMethodName(@RequestBody EmpresaDTO dto) {
        try {
            empresaService.createCompany(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa criada com sucesso!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar criar empresa!\n" + e);
        }
    }

    @GetMapping("listar")
    public ResponseEntity<List<Empresa>> listCompany() {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.listCompany());
    }
    
    
}
