package br.com.estacioneja.controller.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.dto.i.UsuarioDTO;
import br.com.estacioneja.dto.o.UsuarioOutputDTO;
import br.com.estacioneja.services.Usuario.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("listar")
    public ResponseEntity<List<UsuarioOutputDTO>> listUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listUsers());
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<Usuario> getUser(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getUserById(id));
    }

    @PostMapping("criar")
    public ResponseEntity<String> createUser(@RequestBody UsuarioDTO dto) {
        try {
            usuarioService.createUsuario(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso!");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao Criar usuario");
        }
    }

    

    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);

            return ResponseEntity.status(HttpStatus.OK).body("Usuario excluido com sucesso");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir usuario, \n" + e);
        }
    }
}
