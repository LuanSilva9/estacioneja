package br.com.estacioneja.modules.estacionamento.dto;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.modules.empresa.dto.EmpresaOutputDTO;
import br.com.estacioneja.shared.enums.MetodoEntrada;
import br.com.estacioneja.shared.enums.Privacidade;

public record EstacionamentoOutputDTO(
    UUID id,
    String descricao,
    Privacidade privacidade,
    EmpresaOutputDTO empresa,
    List<RegraCapacidadeOutputDTO> regrasCapacidade,
    MetodoEntrada metodoEntrada
) { }
