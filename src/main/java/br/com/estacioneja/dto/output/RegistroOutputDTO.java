package br.com.estacioneja.dto.output;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoRegistro;

public record RegistroOutputDTO(UUID id, VeiculoOutputDTO veiculo, EstacionamentoOutputDTO estacionamento, TipoRegistro tipoRegistro, LocalDateTime dataRegistro) {
    
}
