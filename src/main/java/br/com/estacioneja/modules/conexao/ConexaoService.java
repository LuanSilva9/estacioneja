package br.com.estacioneja.modules.conexao;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.modules.conexao.dto.ConexaoDTO;
import br.com.estacioneja.modules.conexao.dto.ConexaoOutputDTO;
import br.com.estacioneja.modules.conexao.dto.ConexaoUpdateDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConexaoService {
    private final ConexaoRepository conexaoRepository;
    private final ConexaoMapper conexaoMapper;

    /* TRANSACOES */

    @Transactional
    public ConexaoOutputDTO create(ConexaoDTO dto) {
        Conexao conexao = new Conexao(dto.tipoComunicacao(), dto.tipoProtocolo(), dto.endereco(), dto.porta(), dto.credenciais(), dto.enderecoMac());
        return conexaoMapper.toDto(conexaoRepository.save(conexao));
    }

    @Transactional
    public void update(UUID id, ConexaoUpdateDto dto) {
        Conexao conexao = findEntityById(id);

        conexao.setEndereco(dto.endereco());
        conexao.setPorta(dto.porta());

        conexaoRepository.save(conexao);
    }

    @Transactional
    public void delete(UUID id) {
        Conexao conexao = findEntityById(id);
        conexaoRepository.delete(conexao);
    }

    /* CONSULTAS */

    @Transactional(readOnly = true)
    public Conexao findEntityById(UUID id) {
        return conexaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Conexão não encontrada."));
    }

    @Transactional(readOnly = true)
    public ConexaoOutputDTO findById(UUID id) {
        return conexaoMapper.toDto(findEntityById(id));
    }
}
