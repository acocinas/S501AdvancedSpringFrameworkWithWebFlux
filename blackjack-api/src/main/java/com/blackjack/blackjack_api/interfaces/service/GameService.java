package com.blackjack.blackjack_api.interfaces.service;

import com.blackjack.blackjack_api.dto.RankingDTO;
import com.blackjack.blackjack_api.dto.GameResponseDTO;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface GameService {

    Mono<GameResponseDTO> createGame(Long playerId, int bet);

    Mono<GameResponseDTO> getGameById(String gameId);

    Mono<GameResponseDTO> playerHit(String gameId);

    Mono<GameResponseDTO> playerStand(String gameId);

    Mono<Void> deleteGame(String gameId);

    Flux<RankingDTO> getPlayerRanking();
}