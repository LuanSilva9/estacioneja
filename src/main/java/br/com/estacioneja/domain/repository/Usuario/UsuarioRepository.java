package br.com.estacioneja.domain.repository.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
