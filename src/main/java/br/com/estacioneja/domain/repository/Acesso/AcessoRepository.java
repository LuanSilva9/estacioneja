package br.com.estacioneja.domain.repository.Acesso;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.domain.model.Empresa.Empresa;

import java.util.List;
import br.com.estacioneja.modules.usuario.Usuario;

@Repository
public interface AcessoRepository extends JpaRepository<Acesso, UUID> {
    @Query("SELECT a.usuario FROM Acesso a WHERE a.empresa.id = :empresaId")
    List<Usuario> findAllUsersByEmpresa(@Param("empresaId") UUID empresaId);

    List<Acesso> findAllByEmpresa(Empresa empresa);

    Acesso findByUsuarioAndEmpresa(Usuario usuario, Empresa empresa);


    List<Acesso> findAllByUsuario(Usuario usuario);
}
