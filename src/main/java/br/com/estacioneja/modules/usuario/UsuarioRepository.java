package br.com.estacioneja.modules.usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);

    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);

    Boolean existsByEmailAndIdNot(String email, UUID id);
    Boolean existsByCpfAndIdNot(String email, UUID id);

    /* Spring Security */
    @Query("""
        SELECT DISTINCT u
        FROM Usuario u
        LEFT JOIN FETCH u.acessos a
        LEFT JOIN FETCH a.empresa
        WHERE u.email = :email
    """)
    Optional<Usuario> findByEmailWithAcessos(String email);

    @Query("""
        SELECT DISTINCT u
        FROM Usuario u
        LEFT JOIN FETCH u.acessos a
        LEFT JOIN FETCH a.empresa
        WHERE u.id = :id
    """)
    Optional<Usuario> findByIdWithAcessos(UUID id);
}
