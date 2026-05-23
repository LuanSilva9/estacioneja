package br.com.estacioneja.modules.estacionamento;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.empresa.EmpresaMapper;
import br.com.estacioneja.modules.estacionamento.dto.EstacionamentoOutputDTO;
import br.com.estacioneja.modules.estacionamento.dto.RegraCapacidadeOutputDTO;
import br.com.estacioneja.shared.mapper.AbstractMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstacionamentoMapper extends AbstractMapper<Estacionamento, EstacionamentoOutputDTO> {

    private final EmpresaMapper empresaMapper;

    @Override
    public EstacionamentoOutputDTO toDto(Estacionamento estacionamento) {
        if (estacionamento == null) return null;

        return new EstacionamentoOutputDTO(
                estacionamento.getId(),
                estacionamento.getDescricao(),
                estacionamento.getPrivacidade(),
                empresaMapper.toDto(estacionamento.getEmpresa()),
                toRegraCapacidadeList(estacionamento.getRegrasCapacidade()),
                estacionamento.getMetodoEntrada()
        );
    }

    public List<RegraCapacidadeOutputDTO> toRegraCapacidadeList(List<RegraCapacidade> regras) {
        if (regras == null) return List.of();
        return regras.stream()
                .map(r -> new RegraCapacidadeOutputDTO(r.getTipoVeiculo(), r.getCapacidade(), r.getCapacidadeDisponivel()))
                .toList();
    }
}
