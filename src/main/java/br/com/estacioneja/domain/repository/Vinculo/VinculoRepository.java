package br.com.estacioneja.domain.repository.Vinculo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.model.Usuario.Usuario;

@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, UUID> {
    List<Vinculo> findAllByUsuario(Usuario usuario);

    @Query("SELECT v FROM Vinculo v where v.estacionamento.empresa.id = :empresaId")
    List<Vinculo> findByEmpresaId(@Param("empresaId") UUID empresaId);

    Optional<Vinculo> findByEstacionamentoIdAndVeiculoPlaca(UUID estacionamentoId, String placa);
    boolean existsByEstacionamentoIdAndVeiculoPlaca(UUID estacionamentoId, String placa);
}
