package br.com.estacioneja.usecases.interfaces;

import java.util.List;
import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.dto.input.EnderecoDTO;
import br.com.estacioneja.dto.output.EnderecoOutputDTO;

public interface IEndereco {
    Endereco toEntity(EnderecoOutputDTO dto);
    List<Endereco> toEntityList(List<EnderecoOutputDTO> dtoList);
    EnderecoOutputDTO toDto(Endereco entity);

    /* CRUD */
    EnderecoOutputDTO create(EnderecoDTO dto);
    void update(Long id, EnderecoDTO dto);
    void delete(Long id);

    /* Consultas */
    Endereco findEntityById(Long id);
    EnderecoOutputDTO findById(Long id);
}
