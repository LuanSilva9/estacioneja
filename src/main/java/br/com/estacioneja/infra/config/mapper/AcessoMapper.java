package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Acesso.Acesso;
import br.com.estacioneja.dto.output.AcessoOutputDTO;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class, EmpresaMapper.class })
public interface AcessoMapper {
    AcessoOutputDTO toDto(Acesso acesso);

    List<AcessoOutputDTO> toDtoList(List<Acesso> accessos);
}
