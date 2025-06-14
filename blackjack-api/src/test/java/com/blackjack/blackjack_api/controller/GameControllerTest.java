package com.blackjack.blackjack_api.controller;


import com.blackjack.blackjack_api.dto.CreateGameRequestDTO;
import com.blackjack.blackjack_api.dto.GameResponseDTO;
import com.blackjack.blackjack_api.dto.PlayerDTO;
import com.blackjack.blackjack_api.enums.GameStatus;
import com.blackjack.blackjack_api.interfaces.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private WebTestClient client;

    @Mock
    private GameService gameServiceMock;

    @InjectMocks
    private GameController gameController;

    private GameResponseDTO sampleResponse;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToController(gameController)
                .configureClient()
                .baseUrl("/game")
                .build();

        PlayerDTO player = new PlayerDTO(1L, "Fernando", 500);
        sampleResponse = new GameResponseDTO(
                "gameId",
                player,
                List.of(),
                List.of(),
                GameStatus.IN_PROGRESS,
                true,
                52);
    }

    @Test
    void createGame_Returns201AndBody() {
        CreateGameRequestDTO request = new CreateGameRequestDTO(1L, 100);
        Mockito.when(gameServiceMock.createGame(request.getPlayerId(), request.getBet()))
                .thenReturn(Mono.just(sampleResponse));

        client.post()
                .uri("/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.gameId").isEqualTo("gameId")
                .jsonPath("$.player.id").isEqualTo(1)
                .jsonPath("$.status").isEqualTo("IN_PROGRESS");
    }
}