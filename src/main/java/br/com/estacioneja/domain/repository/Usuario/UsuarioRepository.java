package br.com.estacioneja.domain.repository.Usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);

    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);

    Boolean existsByEmailAndIdNot(String email, UUID id);
    Boolean existsByCpfAndIdNot(String email, UUID id);
}
