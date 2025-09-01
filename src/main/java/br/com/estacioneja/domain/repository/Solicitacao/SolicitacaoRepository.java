package br.com.estacioneja.domain.repository.Solicitacao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Solicitacao.Solicitacao;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, UUID>{
 
}
