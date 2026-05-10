package br.com.estacioneja.dto.output;

import java.time.Instant;

public record URLImagemOutputDTO(String url, Instant expiresAt) {
}
