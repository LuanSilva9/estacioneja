package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.TipoUsuario;

public record UsuarioDTO(String name, String cpf, String email, String senha, TipoUsuario tipoUsuario) {
    
}