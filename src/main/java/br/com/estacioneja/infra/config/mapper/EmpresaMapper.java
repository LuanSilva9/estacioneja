package br.com.estacioneja.infra.config.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.estacioneja.domain.model.Empresa.Empresa;
import br.com.estacioneja.dto.output.EmpresaOutputDTO;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    EmpresaOutputDTO toDto(Empresa empresa);

    List<EmpresaOutputDTO> toDtoList(List<Empresa> empresas);

}
