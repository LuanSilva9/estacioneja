package br.com.estacioneja.domain.repository.Estacionamento;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, UUID> {
    List<Estacionamento> findAllByEmpresa(Empresa empresa);
}
