package br.com.estacioneja.domain.repository.Equipamento;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.shared.enums.TipoEquipamento;
import br.com.estacioneja.domain.model.Equipamento.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, UUID> {
    @Query("SELECT COUNT(e) from Equipamento e where e.tipoEquipamento = :tipo and e.estacionamento.id = :estacionamentoId")
    Integer countEquipamentosWhereTipoAndEstacionamento(@Param("tipo") TipoEquipamento tipoEquipamento, @Param("estacionamentoId") UUID estacionamentoId);

    @Query("SELECT e FROM Equipamento e where e.estacionamento.empresa.id = :empresaId")
    List<Equipamento> findByEmpresa(@Param("empresaId") UUID empresaId);
}
