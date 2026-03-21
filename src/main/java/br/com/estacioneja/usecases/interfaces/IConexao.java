package br.com.estacioneja.usecases.interfaces;

import java.util.UUID;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.dto.input.ConexaoDTO;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;
import br.com.estacioneja.dto.update.ConexaoUpdateDto;

public interface IConexao {
    /* CRUD */
    ConexaoOutputDTO create(ConexaoDTO dto);
    void update(UUID id, ConexaoUpdateDto dto);
    void delete(UUID id);

    /* Consultas */
    Conexao findEntityById(UUID id);
    ConexaoOutputDTO findById(UUID id);
}
