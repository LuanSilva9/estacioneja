package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Registro.Registro;
import br.com.estacioneja.dto.output.RegistroOutputDTO;
import br.com.estacioneja.modules.estacionamento.EstacionamentoMapper;
import br.com.estacioneja.modules.veiculo.VeiculoMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegistroMapper extends AbstractMapper<Registro, RegistroOutputDTO> {

    private final VeiculoMapper veiculoMapper;
    private final EstacionamentoMapper estacionamentoMapper;

    @Override
    public RegistroOutputDTO toDto(Registro registro) {
        if (registro == null) return null;
        return new RegistroOutputDTO(
                registro.getId(),
                veiculoMapper.toDto(registro.getVeiculo()),
                estacionamentoMapper.toDto(registro.getEstacionamento()),
                registro.getTipoRegistro(),
                registro.getDataRegistro()
        );
    }
}
