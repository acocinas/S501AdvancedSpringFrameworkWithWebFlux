package com.blackjack.blackjack_api.interfaces.service;

import com.blackjack.blackjack_api.model.Game;
import reactor.core.publisher.Mono;

public interface GameService {

    Mono<Game> createGame(String playerName);

    Mono<Game> getGameById(String gameId);

    Mono<Game> playerHit(String gameId);

    Mono<Game> playerStand(String gameId);

    Mono<Void> deleteGame(String gameId);
}
