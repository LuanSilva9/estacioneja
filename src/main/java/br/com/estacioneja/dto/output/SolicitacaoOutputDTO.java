package br.com.estacioneja.dto.output;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.estacioneja.domain.enums.Situacao;

public record SolicitacaoOutputDTO(UUID id, UsuarioOutputDTO usuario, FilialOutputDTO filial, VeiculoOutputDTO veiculo, Situacao situacao, LocalDateTime createdAt) {
    
}
