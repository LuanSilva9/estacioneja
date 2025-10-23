package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.dto.input.ConexaoDTO;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;
import br.com.estacioneja.usecases.adapter.IBase;

public interface IConexao extends IBase<Conexao, UUID, ConexaoDTO, ConexaoOutputDTO>{
    
}
