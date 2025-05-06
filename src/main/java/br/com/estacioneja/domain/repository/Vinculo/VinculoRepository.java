package br.com.estacioneja.domain.repository.Vinculo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.model.Usuario.Usuario;


@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, Long> {
    List<Vinculo> findAllByUsuario(Usuario usuario);
    Optional<Vinculo> findByUsuarioAndEstacionamento(Usuario usuario, Estacionamento estacionamento);
}
