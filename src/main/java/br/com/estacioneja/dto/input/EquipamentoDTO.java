package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoEquipamento;

public record EquipamentoDTO(String nome, String descricao, String modelo, TipoEquipamento tipoEquipamento, ConexaoDTO conexao, UUID estacionamentoId) {
    
}
