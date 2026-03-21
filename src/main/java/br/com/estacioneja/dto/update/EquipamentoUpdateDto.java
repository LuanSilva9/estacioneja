package br.com.estacioneja.dto.update;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoEquipamento;

public record EquipamentoUpdateDto(String nome, String descricao, String modelo, TipoEquipamento tipoEquipamento, UUID estacionamentoId, ConexaoUpdateDto conexao) {
    
}
