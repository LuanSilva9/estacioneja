package br.com.estacioneja.usecases.interfaces;


import java.util.UUID;

import br.com.estacioneja.domain.enums.TipoUsuario;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.dto.update.UsuarioUpdateDto;

public interface IUsuario {
    void existsEmailOrCpf(String email, String cpf, TipoUsuario tipoUsuario);

    /* CRUD */
    UsuarioOutputDTO create(UsuarioDTO dto);
    void update(UUID id, UsuarioUpdateDto dto);
    void delete(UUID id);

    /* Consultas */
    Usuario findEntityById(UUID id);
    UsuarioOutputDTO findById(UUID id);
}
