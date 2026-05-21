package br.com.estacioneja.dto.input;

import java.util.UUID;

import br.com.estacioneja.shared.enums.TipoEquipamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentoDTO(
    @NotBlank(message = "Nome não pode estar vazio.") String nome,
    @NotBlank(message = "Descrição não pode estar vazia.") String descricao,
    @NotBlank(message = "Modelo não pode estar vazio.") String modelo,
    @NotNull(message = "Tipo de Equipamento não pode estar vazio.") TipoEquipamento tipoEquipamento,
    @NotNull(message = "Conexão não pode estar vazia.") @Valid ConexaoDTO conexao,
    @NotNull(message = "ID do Estacionamento não pode estar vazio.") UUID estacionamentoId
) {

}
