package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.exception.GameNotFoundException;
import com.blackjack.blackjack_api.exception.InvalidPlayException;
import com.blackjack.blackjack_api.exception.PlayerNotFoundException;
import com.blackjack.blackjack_api.interfaces.repository.mongo.GameRepository;
import com.blackjack.blackjack_api.interfaces.repository.mysql.PlayerRepository;
import com.blackjack.blackjack_api.interfaces.service.GameService;
import com.blackjack.blackjack_api.model.Game;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImplement implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameServiceImplement(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Game> createGame(Long playerId) {
       return playerRepository.findById(playerId)
               .switchIfEmpty(Mono.error(new PlayerNotFoundException(playerId)))
               .flatMap(player -> Mono.just(new Game(player.getId())))
               .flatMap(gameRepository::save);
    }

    @Override
    public Mono<Game> getGameById(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)));
    }

    @Override
    public Mono<Game> playerHit(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game -> {
                   game.playerHit();
                   return gameRepository.save(game);})
                    .onErrorMap(IllegalStateException.class,
                            e -> new InvalidPlayException(e.getMessage()));
        }

    @Override
    public Mono<Game> playerStand(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game -> {
                   game.playerStand();
                   return gameRepository.save(game);})
                    .onErrorMap(IllegalStateException.class,
                            e -> new InvalidPlayException(e.getMessage()));
    }

    @Override
    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.deleteById(gameId);
    }
}