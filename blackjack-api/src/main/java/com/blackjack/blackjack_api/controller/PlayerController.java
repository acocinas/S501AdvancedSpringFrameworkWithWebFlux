package com.blackjack.blackjack_api.controller;


import com.blackjack.blackjack_api.model.Player;
import com.blackjack.blackjack_api.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Players", description = "Endpoints para gestionar jugadores")
@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    @Operation(summary = "Lista todos los jugadores", description = "Obtiene todos los jugadores registrados en la base de datos")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public Flux<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @Operation(summary = "Crea un nuevo jugador", description = "Añade un nuevo jugador en la base de datos")
    @ApiResponse(responseCode = "201", description = "CREATED")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Player> createPlayer(
            @Parameter(description = "Jugador a crear", required = true)
            @RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @Operation(summary = "Obtiene un jugador por su id", description = "Recupera un jugador por su id")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @GetMapping("/{id}")
    public Mono<Player> getPlayerById(
            @Parameter(description = "Id del jugador", required = true)
            @PathVariable Long id) {
        return playerService.getPlayerById(id);
    }


    @Operation(summary = "Actualiza un jugador", description = "Actualiza un jugador por su id")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @PutMapping("/{id}")
    public Mono<Player> updatePlayer(
            @Parameter(description = "Id del jugador", required = true)
            @PathVariable Long id,
            @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    @Operation(summary = "Elimina un jugador", description = "Elimina un jugador por su id")
    @ApiResponse(responseCode = "204", description = "NO CONTENT")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletePlayer(
            @Parameter(description = "Id del jugador", required = true)
            @PathVariable Long id) {
        return playerService.deletePlayerById(id);
    }
}
