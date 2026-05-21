package br.com.estacioneja.services.Conexao;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.domain.repository.Conexao.ConexaoRepository;
import br.com.estacioneja.dto.input.ConexaoDTO;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;
import br.com.estacioneja.dto.update.ConexaoUpdateDto;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.infra.config.mapper.ConexaoMapper;
import br.com.estacioneja.usecases.interfaces.IConexao;

@Service
@RequiredArgsConstructor
public class ConexaoService implements IConexao {
    private final ConexaoRepository conexaoRepository;
    private final ConexaoMapper conexaoMapper;

    /* TRANSACOES */
    @Override @Transactional
    public ConexaoOutputDTO create(ConexaoDTO dto) {
        Conexao conexao = new Conexao(dto.tipoComunicacao(), dto.tipoProtocolo(), dto.endereco(), dto.porta(), dto.credenciais(), dto.enderecoMac());

        return conexaoMapper.toDto(conexaoRepository.save(conexao));
    }

    @Override @Transactional
    public void update(UUID id, ConexaoUpdateDto dto) {
        Conexao conexao = findEntityById(id);

        conexao.setEndereco(dto.endereco());
        conexao.setPorta(dto.porta());
        
        conexaoRepository.save(conexao);
    }

    @Override @Transactional
    public void delete(UUID id) {
        Conexao conexao = findEntityById(id);

        conexaoRepository.delete(conexao);
    }

    /* CONSULTAS */

    @Override @Transactional(readOnly = true)
    public Conexao findEntityById(UUID id) {
        return conexaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Conexão não encontrada."));
    }

    @Override @Transactional(readOnly = true)
    public ConexaoOutputDTO findById(UUID id) {
        return conexaoMapper.toDto(findEntityById(id));
    }
    
}
