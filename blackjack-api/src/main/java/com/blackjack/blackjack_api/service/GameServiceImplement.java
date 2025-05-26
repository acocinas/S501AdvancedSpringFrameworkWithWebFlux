package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.dto.GameMapper;
import com.blackjack.blackjack_api.dto.GameResponseDTO;
import com.blackjack.blackjack_api.dto.PlayerDTO;
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
    public Mono<GameResponseDTO> createGame(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(playerId)))
                .flatMap(player -> {
                    Game game = new Game(player.getId());
                    return gameRepository.save(game);
                })
                .flatMap(savedGame ->
                    playerRepository.findById(savedGame.getPlayerId())
                            .switchIfEmpty(Mono.error(new PlayerNotFoundException(savedGame.getPlayerId())))
                            .map(player ->
                                    GameMapper.toDto(
                                            savedGame,
                                            new PlayerDTO(player.getId(), player.getName()))));
                }

        @Override
        public Mono<GameResponseDTO> getGameById (String gameId){
            return gameRepository.findById(gameId)
                    .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                    .flatMap(game ->
                                    playerRepository.findById(game.getPlayerId())
                                            .switchIfEmpty(Mono.error(new PlayerNotFoundException(game.getPlayerId())))
                                    .map(player ->
                                            GameMapper.toDto(
                                            game,
                                            new PlayerDTO(player.getId(), player.getName()))));
        }

        @Override
        public Mono<GameResponseDTO> playerHit (String gameId){
            return gameRepository.findById(gameId)
                    .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                    .flatMap(game -> {
                        game.playerHit();
                        return gameRepository.save(game);
                    })
                    .onErrorMap(IllegalStateException.class,
                            e -> new InvalidPlayException(e.getMessage()))
                    .flatMap(game ->
                            playerRepository.findById(game.getPlayerId())
                                    .switchIfEmpty(Mono.error(new PlayerNotFoundException(game.getPlayerId())))
                            .map(player ->
                                    GameMapper.toDto(
                                    game,
                                    new PlayerDTO(player.getId(), player.getName()))));
        }

        @Override
        public Mono<GameResponseDTO> playerStand (String gameId){
            return gameRepository.findById(gameId)
                    .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                    .flatMap(game -> {
                        game.playerStand();
                        return gameRepository.save(game);
                    })
                    .onErrorMap(IllegalStateException.class,
                            e -> new InvalidPlayException(e.getMessage()))
                    .flatMap(game ->
                            playerRepository.findById(game.getPlayerId())
                                    .switchIfEmpty(Mono.error(new PlayerNotFoundException(game.getPlayerId())))
                                    .map(player ->
                                            GameMapper.toDto(
                                            game,
                                            new PlayerDTO(player.getId(), player.getName()))));
        }

        @Override
        public Mono<Void> deleteGame (String gameId){
            return gameRepository.deleteById(gameId);
        }
    }