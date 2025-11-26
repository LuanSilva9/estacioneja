package br.com.estacioneja.usecases.interfaces;


import br.com.estacioneja.domain.enums.TipoUsuario;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;

public interface IUsuario {
    void existsEmailOrCpf(String email, String cpf, TipoUsuario tipoUsuario);

    /* CRUD */
    UsuarioOutputDTO create(UsuarioDTO dto);
    void update(Long id, UsuarioDTO dto);
    void delete(Long id);

    /* Consultas */
    Usuario findEntityById(Long id);
    UsuarioOutputDTO findById(Long id);
}
