package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.dto.GameMapper;
import com.blackjack.blackjack_api.dto.GameResponseDTO;
import com.blackjack.blackjack_api.dto.PlayerDTO;
import com.blackjack.blackjack_api.dto.RankingDTO;
import com.blackjack.blackjack_api.enums.GameStatus;
import com.blackjack.blackjack_api.exception.GameNotFoundException;
import com.blackjack.blackjack_api.exception.InvalidPlayException;
import com.blackjack.blackjack_api.exception.PlayerNotFoundException;
import com.blackjack.blackjack_api.interfaces.repository.mongo.GameRepository;
import com.blackjack.blackjack_api.interfaces.repository.mysql.PlayerRepository;
import com.blackjack.blackjack_api.interfaces.service.GameService;
import com.blackjack.blackjack_api.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class GameServiceImplement implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameServiceImplement(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<GameResponseDTO> createGame(Long playerId, int bet) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(playerId)))
                .flatMap(player -> {
                    if (player.getBalance() == null || player.getBalance() <= 0) {
                        throw new InvalidPlayException("El jugador no tiene suficiente dinero" + player.getBalance());
                    }
                    if (bet <= 0) {
                        throw new InvalidPlayException("La apuesta debe ser mayor a 0");
                    }
                    if (bet > player.getBalance()) {
                        throw new InvalidPlayException("La apuesta es mayor al saldo " + player.getBalance());
                    }
                    Game game = new Game(player.getId(), bet);
                    game.dealInitialCards();
                    game.checkInitialBlackjack();
                    return gameRepository.save(game)
                            .flatMap(savedGame -> {
                                Mono<Game> gameMono;
                                if (savedGame.getStatus() != GameStatus.IN_PROGRESS) {
                                    gameMono = settleBet(savedGame).thenReturn(savedGame);
                                } else {
                                    gameMono = Mono.just(savedGame);
                                }
                                return gameMono
                                        .flatMap(gameResult ->
                                                playerRepository.findById(gameResult.getPlayerId())
                                                        .switchIfEmpty(Mono.error(new PlayerNotFoundException(gameResult.getPlayerId())))
                                                        .map(playerFound -> GameMapper.toDto(
                                                                gameResult,
                                                                new PlayerDTO(playerFound.getId(), playerFound.getName(), playerFound.getBalance())
                                                        ))
                                        );
                            });
                });
    }

    @Override
    public Mono<GameResponseDTO> getGameById(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game ->
                        playerRepository.findById(game.getPlayerId())
                                .switchIfEmpty(Mono.error(new PlayerNotFoundException(game.getPlayerId())))
                                .map(player ->
                                        GameMapper.toDto(
                                                game,
                                                new PlayerDTO(player.getId(), player.getName(), player.getBalance()))));
    }

    @Override
    public Mono<GameResponseDTO> playerHit(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game -> {
                    game.playerHit();
                    return gameRepository.save(game);
                })
                .flatMap(game -> {
                    if (game.getStatus() != GameStatus.IN_PROGRESS) {
                        return settleBet(game).thenReturn(game);
                    }
                    return Mono.just(game);
                })
                .flatMap(game ->
                        playerRepository.findById(game.getPlayerId())
                                .switchIfEmpty(Mono.error(new PlayerNotFoundException(game.getPlayerId())))
                                .map(player ->
                                        GameMapper.toDto(
                                                game,
                                                new PlayerDTO(player.getId(), player.getName(), player.getBalance()))));
    }

    @Override
    public Mono<GameResponseDTO> playerStand(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game -> {
                    game.playerStand();
                    return gameRepository.save(game);
                })
                .flatMap(game -> settleBet(game).thenReturn(game))
                .flatMap(game ->
                        playerRepository.findById(game.getPlayerId())
                                .switchIfEmpty(Mono.error(new PlayerNotFoundException(game.getPlayerId())))
                                .map(player ->
                                        GameMapper.toDto(
                                                game,
                                                new PlayerDTO(player.getId(), player.getName(), player.getBalance()))));
    }

    @Override
    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.deleteById(gameId);
    }

    @Override
    public Flux<RankingDTO> getPlayerRanking() {
        return gameRepository.findAll()
                .filter(game -> game.getPlayerId() != null)
                .groupBy(Game::getPlayerId)
                .flatMap(groupedFlux ->
                        groupedFlux.collectList()
                                .flatMap(gamesList ->
                                        playerRepository.findById(groupedFlux.key())
                                                .map(player -> {
                                                    long totalGames = gamesList.size();
                                                    long wins = gamesList.stream()
                                                            .filter(game -> game.getStatus() == GameStatus.PLAYER_WIN)
                                                            .count();
                                                    long losses = gamesList.stream()
                                                            .filter(game ->
                                                                    game.getStatus() == GameStatus.DEALER_WIN
                                                                            || game.getStatus() == GameStatus.PLAYER_BUSTED)
                                                            .count();
                                                    long draws = gamesList.stream()
                                                            .filter(game -> game.getStatus() == GameStatus.DRAW)
                                                            .count();
                                                    double winRate = totalGames > 0 ? (wins * 100.0 / totalGames) : 0.0;
                                                    return new RankingDTO(
                                                            player.getId(),
                                                            player.getName(),
                                                            totalGames,
                                                            wins,
                                                            losses,
                                                            draws,
                                                            winRate);
                                                })))
                .sort((r1, r2) -> Double.compare(r2.getWinRate(), r1.getWinRate()));
    }

    private Mono<Void> settleBet(Game game) {
        return playerRepository.findById(game.getPlayerId())
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(game.getPlayerId())))
                .flatMap(player -> {
                    int bet = game.getBet();
                    boolean blackjack = game.isBlackjack();

                    int multiplier;
                    switch (game.getStatus()) {
                        case PLAYER_WIN -> multiplier = blackjack ? 3 : 2;
                        case DRAW -> multiplier = 1;
                        default -> multiplier = 0;
                    }
                    int netDelta = (multiplier - 1) * bet;
                    player.setBalance(player.getBalance() + netDelta);
                    return playerRepository.save(player);
                }).then();
    }
}