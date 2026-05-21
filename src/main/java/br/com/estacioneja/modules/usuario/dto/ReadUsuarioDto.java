package br.com.estacioneja.modules.usuario.dto;

import java.util.UUID;

import br.com.estacioneja.shared.enums.TipoUsuario;

public record ReadUsuarioDto(
        UUID id,
        String name,
        String cpf,
        String telefone,
        String email,
        TipoUsuario tipoUsuario,
        boolean temFotoPerfil
) { }
