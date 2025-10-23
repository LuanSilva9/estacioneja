package br.com.estacioneja.domain.repository.Conexao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacioneja.domain.model.Conexao.Conexao;

@Repository
public interface ConexaoRepository extends JpaRepository<Conexao, UUID> {
    
}
