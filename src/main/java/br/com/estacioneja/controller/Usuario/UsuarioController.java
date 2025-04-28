package br.com.estacioneja.controller.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.dto.UsuarioDTO;
import br.com.estacioneja.services.Usuario.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("criar")
    public ResponseEntity<String> createUser(@RequestBody UsuarioDTO dto) {
        return usuarioService.createUsuario(dto) == HttpStatus.CREATED ? ResponseEntity.ok().body("Usuario Criado!") : ResponseEntity.internalServerError().body("Erro ao criar usuario");
    }
}
