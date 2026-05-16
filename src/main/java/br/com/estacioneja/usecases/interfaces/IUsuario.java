package br.com.estacioneja.usecases.interfaces;


import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.input.UsuarioDTO;
import br.com.estacioneja.dto.output.URLImagemOutputDTO;
import br.com.estacioneja.dto.output.UsuarioOutputDTO;
import br.com.estacioneja.dto.update.UsuarioUpdateDto;

public interface IUsuario {
    /* CRUD */
    UsuarioOutputDTO create(UsuarioDTO dto);
    void update(UUID id, UsuarioUpdateDto dto);
    void delete(UUID id);

    /* Consultas */
    Usuario findEntityById(UUID id);
    UsuarioOutputDTO findById(UUID id);
    UsuarioOutputDTO findByEmail(String email);

    /* Foto de perfil */
    URLImagemOutputDTO uploadFotoPerfil(UUID id, MultipartFile file, Usuario usuarioAutenticado);
    URLImagemOutputDTO getFotoPerfil(UUID id);
    void deleteFotoPerfil(UUID id, Usuario usuarioAutenticado);
}
