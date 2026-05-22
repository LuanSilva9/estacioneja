package br.com.estacioneja.modules.equipamento.dto;

import java.util.UUID;

import br.com.estacioneja.modules.conexao.dto.ConexaoDTO;
import br.com.estacioneja.modules.estacionamento.dto.EstacionamentoOutputDTO;
import br.com.estacioneja.shared.enums.TipoEquipamento;

public record EquipamentoOutputDTO(
        UUID id,
        String nome,
        String descricao,
        String modelo,
        TipoEquipamento tipoEquipamento,
        ConexaoDTO conexaoHardware,
        EstacionamentoOutputDTO estacionamento,
        Boolean ativo
) { }
