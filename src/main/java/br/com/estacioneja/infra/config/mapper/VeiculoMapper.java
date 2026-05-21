package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Veiculo.Veiculo;
import br.com.estacioneja.dto.output.VeiculoOutputDTO;
import br.com.estacioneja.modules.usuario.UsuarioMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VeiculoMapper extends AbstractMapper<Veiculo, VeiculoOutputDTO> {

    private final UsuarioMapper usuarioMapper;

    @Override
    public VeiculoOutputDTO toDto(Veiculo veiculo) {
        if (veiculo == null) return null;
        return new VeiculoOutputDTO(
                veiculo.getId(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getTipoVeiculo(),
                veiculo.getObservacao(),
                usuarioMapper.toDto(veiculo.getUsuario())
        );
    }
}
