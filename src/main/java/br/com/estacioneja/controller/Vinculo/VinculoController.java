package br.com.estacioneja.controller.Vinculo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.i.VinculoDTO;
import br.com.estacioneja.services.Vinculo.VinculoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/vinculo")
public class VinculoController {
    @Autowired
    private VinculoService vinculoService;

    @GetMapping
    public ResponseEntity<List<Vinculo>> getVinculos() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(vinculoService.listVinculos());
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<List<Vinculo>> getVinculosByUser(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(vinculoService.listVinculosByUserId(id));
    }

    @PostMapping("criar")
    public ResponseEntity<String> createVinculo(@RequestBody VinculoDTO dto) {
        try {
            vinculoService.createVinculo(dto);

            return ResponseEntity.status(HttpStatus.OK).body("Usuario Vinculado ao Estacionamento com Sucesso!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel se vincular ao estacionamento");
        }
    }
    
    @DeleteMapping("desvincular")
    public ResponseEntity<String> deleteVinculo(@RequestBody VinculoDTO dto) {
        try {
            vinculoService.deleteVinculo(dto);

            return ResponseEntity.status(HttpStatus.OK).body("Estacionamento desvinculado");    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("Não foi possivel desvincular estacionamento");   
        }
    }
    
}
