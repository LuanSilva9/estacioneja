package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.dto.output.ConexaoOutputDTO;

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
