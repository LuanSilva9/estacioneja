package br.com.estacioneja.modules.conexao;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.conexao.dto.ConexaoOutputDTO;
import br.com.estacioneja.shared.mapper.AbstractMapper;

@Component
public class ConexaoMapper extends AbstractMapper<Conexao, ConexaoOutputDTO> {

    @Override
    public ConexaoOutputDTO toDto(Conexao conexao) {
        if (conexao == null) return null;
        return new ConexaoOutputDTO(
                conexao.getId(),
                conexao.getTipoComunicacao(),
                conexao.getTipoProtocolo(),
                conexao.getEndereco(),
                conexao.getPorta()
        );
    }
}
