package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoEquipamento;
import br.com.estacioneja.dto.input.ConexaoDTO;

public record EquipamentoOutputDTO(UUID id, String nome, String descricao, String modelo, TipoEquipamento tipoEquipamento, ConexaoDTO conexaoHardware, EstacionamentoOutputDTO estacionamento, Boolean ativo) {
    
}
