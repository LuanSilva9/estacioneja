package br.com.estacioneja.modules.empresa.dto;

import br.com.estacioneja.shared.enums.TipoEmpresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmpresaUpdateDto(
    @NotBlank(message = "Nome não pode estar vazio.") String nome, 
    @NotNull(message = "Tipo Empresa não pode estar vazio.") TipoEmpresa tipoEmpresa
) { }