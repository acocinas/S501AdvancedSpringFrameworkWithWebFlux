package com.blackjack.blackjack_api.controller;

import com.blackjack.blackjack_api.dto.CreateGameRequestDTO;
import com.blackjack.blackjack_api.dto.GameResponseDTO;
import com.blackjack.blackjack_api.dto.RankingDTO;
import com.blackjack.blackjack_api.interfaces.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@Validated
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Crea un nuevo juego", description = "Añade un nuevo juego con jugador y apuesta")
    @ApiResponse(responseCode = "201", description = "CREATED successfully")
    @PostMapping("/new")
    public Mono<ResponseEntity<GameResponseDTO>> createGame(
            @Valid @RequestBody CreateGameRequestDTO dto) {
        return gameService.createGame(dto.getPlayerId(), dto.getBet())
            .map(response ->
                    ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(response));
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