package br.com.estacioneja.modules.usuario.dto;

import java.time.Instant;

public record ReadFotoPerfilDto(String url, Instant expiresAt) { }
