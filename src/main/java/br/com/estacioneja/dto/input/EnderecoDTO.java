package br.com.estacioneja.dto.input;

public record EnderecoDTO(String logradouro, String bairro, String cidade, String uf, String cep, Double latitude, Double longitude) {
    
}
