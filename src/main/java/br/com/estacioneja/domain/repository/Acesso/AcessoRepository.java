package br.com.estacioneja.domain.repository.Acesso;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import java.util.List;
import br.com.estacioneja.domain.model.Filial.Filial;
import br.com.estacioneja.domain.model.Usuario.Usuario;


@Repository
public interface AcessoRepository extends JpaRepository<Acesso, UUID> {
    @Query("SELECT a.usuario FROM Acesso a WHERE a.filial.id = :filialId")
    List<Usuario> findAllUsersByFilial(@Param("filialId") UUID filialId);

    List<Acesso> findAllByFilial(Filial filial);
    
    Acesso findByUsuarioAndFilial(Usuario usuario, Filial filial);
}
