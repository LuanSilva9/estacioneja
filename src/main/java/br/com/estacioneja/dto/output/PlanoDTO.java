package br.com.estacioneja.dto.output;

import java.util.List;

public record PlanoDTO(
        Boolean exibirPagina,
        String nome,
        String titulo,
        Long recomendadoParaAte,
        List<String> beneficios,
        Long custoPorMes,
        Boolean estoqueAtivo
) {}