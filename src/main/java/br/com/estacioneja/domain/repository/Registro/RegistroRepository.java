package br.com.estacioneja.domain.repository.Registro;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.enums.TipoRegistro;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;


@Repository
public interface RegistroRepository extends JpaRepository<Registro, UUID>{
    Registro findByVeiculoAndEstacionamentoAndTipoRegistro(Veiculo veiculo, Estacionamento estacionamento, TipoRegistro tipoRegistro);
}
