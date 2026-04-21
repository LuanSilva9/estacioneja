package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.dto.output.EnderecoOutputDTO;

@Component
public class EnderecoMapper extends AbstractMapper<Endereco, EnderecoOutputDTO> {

    @Override
    public EnderecoOutputDTO toDto(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoOutputDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep(),
                endereco.getLatitude(),
                endereco.getLongitude()
        );
    }
}
