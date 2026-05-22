package br.com.estacioneja.modules.endereco.dto;

public record EnderecoOutputDTO(
        Long id,
        String logradouro,
        String bairro,
        String cidade,
        String uf,
        String cep,
        Double latitude,
        Double longitude
) { }
