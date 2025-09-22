package br.com.estacioneja.domain.repository.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estacioneja.domain.model.Endereco.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
