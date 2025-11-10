package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoRegistro;

public record RegistroDTO(String placa, UUID estacionamentoId, TipoRegistro tipoRegistro) {
    
}
