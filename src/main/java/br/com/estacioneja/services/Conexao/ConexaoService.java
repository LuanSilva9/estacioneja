package br.com.estacioneja.services.Conexao;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.domain.repository.Conexao.ConexaoRepository;
import br.com.estacioneja.dto.input.ConexaoDTO;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;
import br.com.estacioneja.exceptions.custom.ConectionNotFoundException;
import br.com.estacioneja.infra.config.mapper.ConexaoMapper;
import br.com.estacioneja.usecases.interfaces.IConexao;

@Service
public class ConexaoService implements IConexao {
    private final ConexaoRepository conexaoRepository;
    private final ConexaoMapper conexaoMapper;

    public ConexaoService(ConexaoRepository conexaoRepository, ConexaoMapper conexaoMapper) {
        this.conexaoRepository = conexaoRepository;
        this.conexaoMapper = conexaoMapper;
    }

    /* TRANSACOES */
    @Override
    public ConexaoOutputDTO create(ConexaoDTO dto) {
        Conexao conexao = new Conexao(dto);

        return conexaoMapper.toDto(conexaoRepository.save(conexao));
    }

    @Override
    public ConexaoOutputDTO update(UUID id, ConexaoDTO dto) {
        Conexao conexao = findEntityById(id);

        conexao.setEndereco(dto.endereco());
        conexao.setPorta(dto.porta());
        
        return conexaoMapper.toDto(conexaoRepository.save(conexao));
    }

    @Override
    public void delete(UUID id) {
        Conexao conexao = findEntityById(id);

        conexaoRepository.delete(conexao);
    }

    /* CONSULTAS */

    @Override
    public Conexao findEntityById(UUID id) {
        return conexaoRepository.findById(id).orElseThrow(ConectionNotFoundException::new);
    }

    @Override
    public ConexaoOutputDTO findById(UUID id) {
        return conexaoMapper.toDto(findEntityById(id));
    }
    
}
