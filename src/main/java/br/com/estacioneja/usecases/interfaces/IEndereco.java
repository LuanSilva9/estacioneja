package br.com.estacioneja.usecases.interfaces;

import br.com.estacioneja.domain.model.Endereco.Endereco;
import br.com.estacioneja.dto.input.EnderecoDTO;
import br.com.estacioneja.dto.output.EnderecoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IEndereco extends IBase<Endereco, Long, EnderecoDTO, EnderecoOutputDTO> {
    
}
