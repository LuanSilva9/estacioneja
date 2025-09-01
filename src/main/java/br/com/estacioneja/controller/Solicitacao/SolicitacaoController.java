package br.com.estacioneja.controller.Solicitacao;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.input.SolicitacaoDTO;
import br.com.estacioneja.dto.output.SolicitacaoOutputDTO;
import br.com.estacioneja.services.Solicitacao.SolicitacaoService;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/solicitacoes")
public class SolicitacaoController {
    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoOutputDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(solicitacaoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SolicitacaoOutputDTO> criar(@RequestBody SolicitacaoDTO dto) {
        SolicitacaoOutputDTO solicitacao = this.solicitacaoService.create(dto);
        
        URI location = URI.create(String.format("/api/v1/solicitacoes/%s",  solicitacao.id()));
        
        return ResponseEntity.created(location).body(solicitacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoOutputDTO> atualizar(@PathVariable UUID id, @RequestBody SolicitacaoDTO dto) {
        return ResponseEntity.ok().body(this.solicitacaoService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        this.solicitacaoService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
