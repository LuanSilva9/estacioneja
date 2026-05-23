package br.com.estacioneja.modules.vinculo.dto;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.modules.endereco.Endereco;
import br.com.estacioneja.modules.estacionamento.dto.RegraCapacidadeOutputDTO;
import br.com.estacioneja.shared.enums.Privacidade;

public record EstacionamentoCardUsuarioDTO(
        UUID id,
        String descricao,
        String nomeEmpresa,
        Endereco endereco,
        Privacidade privacidade,
        List<RegraCapacidadeOutputDTO> regrasCapacidade
) { }
