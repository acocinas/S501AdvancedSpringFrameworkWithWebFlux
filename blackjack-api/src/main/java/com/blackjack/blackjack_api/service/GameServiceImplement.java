package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.interfaces.repository.mongo.GameRepository;
import com.blackjack.blackjack_api.interfaces.service.GameService;
import com.blackjack.blackjack_api.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameServiceImplement implements GameService {

    private final GameRepository gameRepository;

    @Override
    public Mono<Game> createGame(String playerName) {
        Game game = new Game();
        return gameRepository.save(game);
    }

    @Override
    public Mono<Game> getGameById(String gameId) {
        return gameRepository.findById(gameId);
    }

    @Override
    public Mono<Game> playerHit(String gameId) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    try {
                        game.playerHit();
                        return gameRepository.save(game);
                    } catch (IllegalStateException e) {
                        return Mono.error(e);
                    }
                });
        }

    @Override
    public Mono<Game> playerStand(String gameId) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {
                    try {
                        game.playerStand();
                        return gameRepository.save(game);
                    } catch (IllegalStateException e) {
                        return Mono.error(e);
                    }
                });
    }

    @Override
    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.deleteById(gameId);
    }
}