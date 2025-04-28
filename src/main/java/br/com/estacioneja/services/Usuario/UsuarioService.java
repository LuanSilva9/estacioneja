package br.com.estacioneja.services.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.UsuarioDTO;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public HttpStatus createUsuario(UsuarioDTO dto) {
        Usuario newUsuario = new Usuario(dto);

        this.usuarioRepository.save(newUsuario);

        return HttpStatus.CREATED;
    }
}
