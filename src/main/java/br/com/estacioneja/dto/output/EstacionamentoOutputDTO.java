package br.com.estacioneja.dto.output;

import java.util.UUID;

import br.com.estacioneja.domain.enums.Plano;
import br.com.estacioneja.domain.enums.Privacidade;


public record EstacionamentoOutputDTO(UUID id, String descricao, Privacidade privacidade, Plano plano, EnderecoOutputDTO endereco, Long capacidade, String prefixo, EmpresaOutputDTO empresa) {

}
