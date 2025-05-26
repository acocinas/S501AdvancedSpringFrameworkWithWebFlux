package com.blackjack.blackjack_api.interfaces.service;

import com.blackjack.blackjack_api.dto.GameResponseDTO;
import com.blackjack.blackjack_api.model.Game;
import reactor.core.publisher.Mono;

public interface GameService {

    Mono<GameResponseDTO> createGame(Long playerId);

    Mono<GameResponseDTO> getGameById(String gameId);

    Mono<GameResponseDTO> playerHit(String gameId);

    Mono<GameResponseDTO> playerStand(String gameId);

    Mono<Void> deleteGame(String gameId);
}