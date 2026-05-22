package br.com.estacioneja.modules.vinculo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.modules.usuario.Usuario;

@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, UUID> {
    List<Vinculo> findAllByUsuario(Usuario usuario);

    @Query("SELECT v FROM Vinculo v where v.estacionamento.empresa.id = :empresaId")
    List<Vinculo> findByEmpresaId(@Param("empresaId") UUID empresaId);

    Optional<Vinculo> findByEstacionamentoIdAndVeiculoPlaca(UUID estacionamentoId, String placa);
    boolean existsByEstacionamentoIdAndVeiculoPlaca(UUID estacionamentoId, String placa);
}
