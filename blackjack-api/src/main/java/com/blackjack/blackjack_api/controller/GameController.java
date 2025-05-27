package com.blackjack.blackjack_api.controller;

import com.blackjack.blackjack_api.dto.GameResponseDTO;
import com.blackjack.blackjack_api.dto.RankingDTO;
import com.blackjack.blackjack_api.interfaces.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Operation(summary = "Crea un nuevo juego", description = "Añade un nuevo juego con jugador y apuesta")
    @ApiResponse(responseCode = "201", description = "CREATED successfully")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponseDTO> createGame(
            @RequestParam("playerId") Long playerId,
            @RequestParam("bet") int bet) {
        return gameService.createGame(playerId, bet);
    }

    @Operation(summary = "Obtiene un juego por su id")
    @GetMapping("/{gameId}")
    public Mono<GameResponseDTO> getGameById(@PathVariable String gameId) {
        return gameService.getGameById(gameId);
    }

    @Operation(summary = "Pide carta")
    @PostMapping("/{gameId}/play/hit")
    public Mono<GameResponseDTO> playerHit(@PathVariable String gameId) {
        return gameService.playerHit(gameId);
    }

    @Operation(summary = "Se planta")
    @PostMapping("/{gameId}/play/stand")
    public Mono<GameResponseDTO> playerStand(@PathVariable String gameId) {
        return gameService.playerStand(gameId);
    }

    @Operation(summary = "Elimina el juego por ID")
    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGame(@PathVariable String gameId) {
        return gameService.deleteGame(gameId);
    }

    @Operation(summary = "Obtiene el ranking de jugadores")
    @GetMapping("/ranking")
    public Flux<RankingDTO> getPlayerRanking() {
        return gameService.getPlayerRanking();
    }
}