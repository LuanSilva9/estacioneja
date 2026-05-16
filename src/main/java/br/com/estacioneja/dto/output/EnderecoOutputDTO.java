package br.com.estacioneja.dto.output;

public record EnderecoOutputDTO(Long id, String logradouro, String bairro, String cidade, String uf, String cep, Double latitude, Double longitude) {
    
}
