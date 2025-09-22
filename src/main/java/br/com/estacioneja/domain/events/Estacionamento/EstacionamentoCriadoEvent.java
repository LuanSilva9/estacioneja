package br.com.estacioneja.domain.events.Estacionamento;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.dto.output.UsuarioOutputDTO;

public record EstacionamentoCriadoEvent(List<UsuarioOutputDTO> usuariosVinculo, UUID estacionamentoId) {
    
}
