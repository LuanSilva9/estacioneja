package br.com.estacioneja.modules.veiculo;

import org.springframework.stereotype.Component;

import br.com.estacioneja.modules.usuario.UsuarioMapper;
import br.com.estacioneja.modules.veiculo.dto.VeiculoOutputDTO;
import br.com.estacioneja.shared.mapper.AbstractMapper;
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
