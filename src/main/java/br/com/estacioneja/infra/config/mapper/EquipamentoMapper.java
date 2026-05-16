package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Conexao.Conexao;
import br.com.estacioneja.domain.model.Equipamento.Equipamento;
import br.com.estacioneja.dto.input.ConexaoDTO;
import br.com.estacioneja.dto.output.EquipamentoOutputDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EquipamentoMapper extends AbstractMapper<Equipamento, EquipamentoOutputDTO> {

    private final EstacionamentoMapper estacionamentoMapper;

    @Override
    public EquipamentoOutputDTO toDto(Equipamento equipamento) {
        if (equipamento == null) return null;
        return new EquipamentoOutputDTO(
                equipamento.getId(),
                equipamento.getNome(),
                equipamento.getDescricao(),
                equipamento.getModelo(),
                equipamento.getTipoEquipamento(),
                toConexaoDto(equipamento.getConexaoHardware()),
                estacionamentoMapper.toDto(equipamento.getEstacionamento()),
                equipamento.getAtivo()
        );
    }

    private ConexaoDTO toConexaoDto(Conexao conexao) {
        if (conexao == null) return null;
        return new ConexaoDTO(
                conexao.getTipoComunicacao(),
                conexao.getTipoProtocolo(),
                conexao.getEndereco(),
                conexao.getPorta(),
                conexao.getCredenciais(),
                conexao.getEnderecoMac()
        );
    }
}
