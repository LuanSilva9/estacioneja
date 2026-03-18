package br.com.estacioneja.domain.repository.Estacionamento;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.domain.model.Estacionamento.Estacionamento;
import br.com.estacioneja.domain.enums.Privacidade;
import java.util.List;


@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, UUID> {
    List<Estacionamento> findByPrivacidade(Privacidade privacidade);
    List<Estacionamento> findAllByEmpresa(Empresa empresa);
}  
