package br.com.estacioneja.controller.Autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.repository.Usuario.UsuarioRepository;
import br.com.estacioneja.dto.AuthDTO;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("auth")
public class AutenticacaoController {
    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("sign") 
    public ResponseEntity<Usuario> auth(@RequestBody AuthDTO dto) throws Exception {
        Usuario userFind = usuarioRepository.findByEmailAndSenha(dto.email(), dto.senha()).orElseThrow(() -> new Exception("Usuario não encontrado"));
        
        if(userFind == null) {
            new Exception("Usuario não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userFind);
    }
}
