package com.blackjack.blackjack_api.exception;

public class PlayerNotFoundException extends RuntimeException {
    private final Long playerId;

    public PlayerNotFoundException(Long playerId) {
        super("Jugador no encontrado: " + playerId);
        this.playerId = playerId;
    }

    public Long getPlayerId() {
        return playerId;
    }
}
