package br.com.estacioneja.domain.repository.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Empresa.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Boolean existsByCnpj(String cnpj);
}
