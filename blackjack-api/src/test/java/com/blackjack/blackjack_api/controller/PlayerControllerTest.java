package com.blackjack.blackjack_api.controller;


import com.blackjack.blackjack_api.exception.PlayerNotFoundException;
import com.blackjack.blackjack_api.model.Player;
import com.blackjack.blackjack_api.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerServiceMock;

    @InjectMocks
    private PlayerController playerControllerMock;

    private WebTestClient webTestClient;
    private Player player;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(playerControllerMock)
                .controllerAdvice(new com.blackjack.blackjack_api.exception.handler.GlobalExceptionHandler())
                .build();
        player = new Player(1L, "Alfonso", 500);
    }

    @Test
    void getPlayerById_whenExists_returnsPlayer() {
        when(playerServiceMock.getPlayerById(1L)).thenReturn(Mono.just(player));
        webTestClient.get().uri("/players/1").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Alfonso")
                .jsonPath("$.score").isEqualTo(500);
        verify(playerServiceMock).getPlayerById(1L);
    }

    @Test
    void getPlayerById_whenNotExists_returns404() {
        when(playerServiceMock.getPlayerById(2L))
                .thenReturn(Mono.error(new PlayerNotFoundException(1L)));
        webTestClient.get().uri("/players/2")
                .exchange()
                .expectStatus().isNotFound();
        verify(playerServiceMock).getPlayerById(2L);
    }

    @Test
    void createPlayer_returnsCreatedPlayer() {
        Player toCreatePlayer = new Player(null, "Marta", 500);
        Player createdPlayer = new Player(2L, "Marta", 500);

        when(playerServiceMock.createPlayer(toCreatePlayer)).thenReturn(Mono.just(createdPlayer));
        webTestClient.post().uri("/players")
                .bodyValue(toCreatePlayer)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(2)
                .jsonPath("$.name").isEqualTo("Marta")
                .jsonPath("$.score").isEqualTo(500);
        verify(playerServiceMock).createPlayer(toCreatePlayer);
    }

    @Test
    void updatePlayer_whenExists_returnsUpdatedPlayer() {
        Player updatePlayer = new Player(null, "Escu", 700);
        Player updatedPlayer = new Player(1L, "Escu", 700);
        when(playerServiceMock.updatePlayer(1L, updatePlayer)).thenReturn(Mono.just(updatedPlayer));
        webTestClient.put().uri("/players/1")
                .bodyValue(updatePlayer)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Escu")
                .jsonPath("$.score").isEqualTo(700);
        verify(playerServiceMock).updatePlayer(1L, updatePlayer);
    }

    @Test
    void updatePlayer_whenNotExists_returns404() {
        Player updatePlayer = new Player(null, "Escu", 700);
        when(playerServiceMock.updatePlayer(47L, updatePlayer))
                .thenReturn(Mono.error(new PlayerNotFoundException(47L)));
        webTestClient.put().uri("/players/47")
                .bodyValue(updatePlayer)
                .exchange()
                .expectStatus().isNotFound();
        verify(playerServiceMock).updatePlayer(47L, updatePlayer);
    }

    @Test
    void deletePlayer_whenExists_returnsNoContent() {
        when(playerServiceMock.deletePlayerById(1L)).thenReturn(Mono.empty());
        webTestClient.delete().uri("/players/1")
                .exchange()
                .expectStatus().isNoContent();
        verify(playerServiceMock).deletePlayerById(1L);
    }

    @Test
    void deletePlayer_whenNotExists_returns404() {
        when(playerServiceMock.deletePlayerById(47L))
                .thenReturn(Mono.error(new PlayerNotFoundException(47L)));
        webTestClient.delete().uri("/players/47")
                .exchange()
                .expectStatus().isNotFound();
        verify(playerServiceMock).deletePlayerById(47L);
    }
}