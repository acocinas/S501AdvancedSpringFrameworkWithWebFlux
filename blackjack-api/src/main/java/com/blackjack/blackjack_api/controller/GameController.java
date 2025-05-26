package com.blackjack.blackjack_api.controller;

import com.blackjack.blackjack_api.dto.GameResponseDTO;
import com.blackjack.blackjack_api.interfaces.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponseDTO> createGame(@RequestParam Long playerId) {
        return gameService.createGame(playerId);
    }

    @GetMapping("/{gameId}")
    public Mono<GameResponseDTO> getGameById(@PathVariable String gameId) {
        return gameService.getGameById(gameId);
    }

    @PostMapping("/{gameId}/play/hit")
    public Mono<GameResponseDTO> playerHit(@PathVariable String gameId) {
        return gameService.playerHit(gameId);
    }

    @PostMapping("/{gameId}/play/stand")
    public Mono<GameResponseDTO> playerStand(@PathVariable String gameId) {
        return gameService.playerStand(gameId);
    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGame(@PathVariable String gameId) {
        return gameService.deleteGame(gameId);
    }
}