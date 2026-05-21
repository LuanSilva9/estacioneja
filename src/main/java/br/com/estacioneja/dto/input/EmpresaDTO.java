package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.shared.enums.Plano;
import br.com.estacioneja.shared.enums.TipoEmpresa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmpresaDTO(
    @NotNull(message = "ID do Representante não pode estar vazio.")
    UUID representanteId, 
    
    @NotBlank(message = "Nome não pode estar vazio.")
    String nome, 

    @NotNull(message = "Endereço não pode estar vazio.")
    @Valid
    EnderecoDTO endereco, 

    @NotNull(message = "Tipo Empresa não pode estar vazio.")
    TipoEmpresa tipoEmpresa, 

    @NotBlank(message = "CNPJ não pode estar vazio.")
    String cnpj, 

    @NotBlank(message = "Prefixo não pode estar vazio.")
    String prefixo, 

    @NotNull(message = "Plano não pode estar vazio.")
    Plano plano, 

    UUID empresaId

) { }