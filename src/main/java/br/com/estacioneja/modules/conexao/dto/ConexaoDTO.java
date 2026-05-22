package br.com.estacioneja.modules.conexao.dto;

import br.com.estacioneja.shared.enums.TipoComunicacao;
import br.com.estacioneja.shared.enums.TipoProtocolo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConexaoDTO(
    @NotNull(message = "Tipo de Comunicação não pode estar vazio.") TipoComunicacao tipoComunicacao,
    @NotNull(message = "Tipo de Protocolo não pode estar vazio.") TipoProtocolo tipoProtocolo,
    @NotBlank(message = "Endereço não pode estar vazio.") String endereco,
    @NotNull(message = "Porta não pode estar vazia.") Integer porta,
    @NotBlank(message = "Credenciais não podem estar vazias.") String credenciais,
    @NotBlank(message = "Endereço MAC não pode estar vazio.") String enderecoMac
) { }
