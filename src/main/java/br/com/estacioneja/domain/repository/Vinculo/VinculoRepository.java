package br.com.estacioneja.domain.repository.Vinculo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;
import br.com.estacioneja.domain.model.Veiculo.Veiculo;

@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, UUID> {
    List<Vinculo> findAllByUsuario(Usuario usuario);
    
    Boolean existsByEstacionamentoAndVeiculo(Estacionamento estacionamento, Veiculo veiculo);
}
