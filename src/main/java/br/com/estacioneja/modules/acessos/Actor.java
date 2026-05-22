package br.com.estacioneja.modules.acessos;

import java.util.UUID;

public class Actor {
    private final UUID usuarioId;
    private final boolean sistema;

    private Actor(UUID usuarioId, boolean sistema) {
        this.usuarioId = usuarioId;
        this.sistema = sistema;
    }

    public static Actor usuario(UUID id) {
        return new Actor(id, false);
    }

    public static Actor sistema() {
        return new Actor(null, true);
    }

    public boolean isSistema() {
        return sistema;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }
}