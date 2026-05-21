package br.com.estacioneja.infra.config.mapper;

import org.springframework.stereotype.Component;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.dto.output.AcessoOutputDTO;
import br.com.estacioneja.modules.usuario.UsuarioMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AcessoMapper extends AbstractMapper<Acesso, AcessoOutputDTO> {

    private final UsuarioMapper usuarioMapper;
    private final EmpresaMapper empresaMapper;

    @Override
    public AcessoOutputDTO toDto(Acesso acesso) {
        if (acesso == null) return null;
        return new AcessoOutputDTO(
                acesso.getId(),
                acesso.getTipoAcesso(),
                usuarioMapper.toDto(acesso.getUsuario()),
                empresaMapper.toDto(acesso.getEmpresa())
        );
    }
}
