package br.com.estacioneja.domain.events.EstacionamentoCriado;

import java.util.List;
import java.util.UUID;

import br.com.estacioneja.dto.output.UsuarioOutputDTO;

public record EstacionamentoCriadoEvent(List<UsuarioOutputDTO> usuariosVinculo, UUID estacionamentoId) {
    
}
