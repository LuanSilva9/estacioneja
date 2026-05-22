package br.com.estacioneja.domain.repository.Registro;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.modules.estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.modules.veiculo.Veiculo;


@Repository
public interface RegistroRepository extends JpaRepository<Registro, UUID>{
    Registro findTopByVeiculoAndEstacionamentoOrderByDataRegistroDesc(Veiculo veiculo, Estacionamento estacionamento);

    List<Registro> findByVeiculoUsuarioIdOrderByDataRegistroDesc(UUID usuarioId);

    List<Registro> findByEstacionamentoIdOrderByDataRegistroDesc(UUID estacionamentoId);
}
