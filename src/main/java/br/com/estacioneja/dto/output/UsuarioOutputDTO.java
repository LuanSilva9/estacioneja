package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoUsuario;

public record UsuarioOutputDTO(UUID id, String name, String cpf, String email, TipoUsuario tipoUsuario) {
    
}
