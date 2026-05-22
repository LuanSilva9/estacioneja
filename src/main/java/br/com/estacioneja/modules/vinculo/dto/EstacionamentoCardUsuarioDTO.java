package br.com.estacioneja.modules.vinculo.dto;

import java.util.UUID;

public record EstacionamentoCardUsuarioDTO(
        UUID id,
        String descricao,
        String nomeEmpresa,
        String cidade,
        String uf,
        Long capacidade,
        Long capacidadeDisponivel
) { }
