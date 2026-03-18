package br.com.estacioneja.dto.input;

import br.com.estacioneja.domain.enums.TipoUsuario;

public record UsuarioDTO(String name, String cpf, String telefone, String email, String senha, TipoUsuario tipoUsuario) {
    
}