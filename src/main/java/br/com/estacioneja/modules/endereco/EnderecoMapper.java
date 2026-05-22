package br.com.estacioneja.modules.endereco;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.endereco.dto.EnderecoOutputDTO;
import br.com.estacioneja.shared.mapper.AbstractMapper;

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
