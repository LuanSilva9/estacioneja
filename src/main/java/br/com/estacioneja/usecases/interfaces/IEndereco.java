package br.com.estacioneja.usecases.interfaces;

import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.dto.input.EnderecoDTO;
import br.com.estacioneja.dto.output.EnderecoOutputDTO;
import br.com.estacioneja.dto.update.EnderecoUpdateDto;

public interface IEndereco {
    /* CRUD */
    Endereco create(EnderecoDTO dto);
    void update(Long id, EnderecoUpdateDto dto);
    void delete(Long id);

    /* Consultas */
    Endereco findEntityById(Long id);
    EnderecoOutputDTO findById(Long id);
}
