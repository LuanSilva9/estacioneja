package br.com.estacioneja.controller.Planos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.dto.output.PlanoDTO;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/planos")
public class PlanosController {
    @GetMapping
    public ResponseEntity<List<PlanoDTO>> listarPlanos() {
        List<PlanoDTO> planos = Arrays.stream(Plano.values()).map(plano -> new PlanoDTO(
                        plano.getExibirPagina(),
                        plano.name(),
                        plano.getTitulo(),
                        plano.getRecomendadoParaAte(),
                        plano.getBeneficios(),
                        plano.getCustoPorMes(),
                        plano.getEstoqueAtivo()
                )).toList();

        return ResponseEntity.ok(planos);
    }
}
