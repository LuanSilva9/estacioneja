package br.com.estacioneja.dto.update;

public record EnderecoUpdateDto(String logradouro, String bairro, String cidade, String uf, String cep, Double latitude, Double longitude) {
    
}
