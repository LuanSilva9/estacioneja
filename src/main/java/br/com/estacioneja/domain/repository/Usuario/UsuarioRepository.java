package br.com.estacioneja.domain.repository.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
