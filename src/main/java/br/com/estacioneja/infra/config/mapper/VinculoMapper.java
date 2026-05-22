package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Vinculo.Vinculo;
import br.com.estacioneja.dto.output.VinculoOutputDTO;
import br.com.estacioneja.modules.estacionamento.EstacionamentoMapper;
import br.com.estacioneja.modules.veiculo.VeiculoMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VinculoMapper extends AbstractMapper<Vinculo, VinculoOutputDTO> {

    private final EstacionamentoMapper estacionamentoMapper;
    private final VeiculoMapper veiculoMapper;

    @Override
    public VinculoOutputDTO toDto(Vinculo vinculo) {
        if (vinculo == null) return null;
        return new VinculoOutputDTO(
                vinculo.getId(),
                veiculoMapper.toDto(vinculo.getVeiculo()),
                estacionamentoMapper.toDto(vinculo.getEstacionamento())
        );
    }
}
