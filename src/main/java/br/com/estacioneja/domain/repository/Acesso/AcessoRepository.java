package br.com.estacioneja.domain.repository.Acesso;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Acesso.Acesso;

@Repository
public interface AcessoRepository extends JpaRepository<Acesso, UUID> {
    
}
