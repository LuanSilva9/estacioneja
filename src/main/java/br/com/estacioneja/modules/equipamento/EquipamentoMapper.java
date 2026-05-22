package br.com.estacioneja.modules.equipamento;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.conexao.Conexao;
import br.com.estacioneja.modules.conexao.dto.ConexaoDTO;
import br.com.estacioneja.modules.equipamento.dto.EquipamentoOutputDTO;
import br.com.estacioneja.modules.estacionamento.EstacionamentoMapper;
import br.com.estacioneja.shared.mapper.AbstractMapper;
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
