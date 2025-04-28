package br.com.estacioneja.domain.repository.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Empresa.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
}
