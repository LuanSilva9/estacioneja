package br.com.estacioneja.domain.repository.Registro;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Registro.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, UUID>{
    
}
