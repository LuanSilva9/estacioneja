package br.com.estacioneja.controller.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.dto.i.EstacionamentoDTO;
import br.com.estacioneja.services.Estacionamento.EstacionamentoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("api/estacionamento")
public class EstacionamentoController {
    @Autowired private EstacionamentoService estacionamentoService;

    @GetMapping("listar")
    public ResponseEntity<List<Estacionamento>> listarEstacionamentos() {
        return ResponseEntity.status(HttpStatus.OK).body(estacionamentoService.listEstacionamentos());
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<Estacionamento> listarEstacionamentosPorId(@PathVariable UUID id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(estacionamentoService.listEstacionamentoById(id));
    }

    @GetMapping("listar/empresa/{id}")
    public ResponseEntity<List<Estacionamento>> listarEstacionamentosPorEmpresas(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(estacionamentoService.listEstacionamentosByCompany(id));
    }
    
    @PostMapping("criar")
    public ResponseEntity<String> criarEstacionamento(@RequestBody EstacionamentoDTO dto) {
        try {
            estacionamentoService.createEstacionamento(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Estacionamento Criado!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel criar o Estacionamento, \n" + e);
        }
    }

    @PutMapping("updt/{id}")
    public ResponseEntity<String> atualizarEstacionamento(@PathVariable UUID id, @RequestBody EstacionamentoDTO dto) {
        try {
            estacionamentoService.updateEstacionamento(id, dto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Estacionamento Atualizado!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel atualizar o Estacionamento, \n" + e);
        }
    }

    @DeleteMapping("delt/{id}")
    public ResponseEntity<String> deletarEstacionamento(@PathVariable UUID id) {
        try {
            estacionamentoService.deleteEstacionamento(id);

            return ResponseEntity.status(HttpStatus.CREATED).body("Estacionamento Deletado!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel deletar o Estacionamento, \n" + e);
        }
    }
}
