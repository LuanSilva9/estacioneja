package br.com.estacioneja.usecases.interfaces;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IUsuario extends IBase<Usuario, Long, UsuarioDTO, UsuarioOutputDTO>{

}
