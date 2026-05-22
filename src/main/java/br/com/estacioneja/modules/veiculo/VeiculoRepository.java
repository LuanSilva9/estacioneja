package br.com.estacioneja.modules.veiculo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.modules.usuario.Usuario;

public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    List<Veiculo> findByUsuario(Usuario usuario);
    Optional<Veiculo> findByPlaca(String placa);
}
