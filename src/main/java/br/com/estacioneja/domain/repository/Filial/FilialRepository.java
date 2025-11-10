package br.com.estacioneja.domain.repository.Filial;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Filial.Filial;

@Repository
public interface FilialRepository extends JpaRepository<Filial, UUID> {
    Boolean existsByCnpj(String cnpj);
    List<Filial> findAllByEmpresaId(Long empresaId);
}
