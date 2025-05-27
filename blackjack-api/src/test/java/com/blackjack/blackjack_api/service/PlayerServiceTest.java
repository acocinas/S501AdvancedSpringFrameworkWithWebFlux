package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.exception.PlayerNotFoundException;
import com.blackjack.blackjack_api.interfaces.repository.mysql.PlayerRepository;
import com.blackjack.blackjack_api.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepositoryMock;

    @InjectMocks
    private PlayerService playerServiceMock;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(1L, "Alfonso", 500);
    }

    @Test
    void testGetPlayerById_whenExists() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.just(player));
        StepVerifier.create(playerServiceMock.getPlayerById(1L))
                .expectNext(player)
                .verifyComplete();
    }

    @Test
    void testGetPlayerById_whenNotExists() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(playerServiceMock.getPlayerById(1L))
                .expectError(PlayerNotFoundException.class)
                .verify();
    }

    @Test
    void testCreatePlayer() {
        when(playerRepositoryMock.save(any(Player.class))).thenReturn(Mono.just(player));
        StepVerifier.create(playerServiceMock.createPlayer(player))
                .expectNext(player)
                .verifyComplete();
    }

    @Test
    void testUpdatePlayer_whenExists() {
       Player updatedPlayer = new Player(1L, "Alfonso", 1000);
       when(playerRepositoryMock.findById(1L)).thenReturn(Mono.just(player));
       when(playerRepositoryMock.save(any(Player.class))).thenReturn(Mono.just(updatedPlayer));
       StepVerifier.create(playerServiceMock.updatePlayer(1L, updatedPlayer))
               .expectNextMatches(p -> p.getName().equals("Alfonso") && p.getBalance() == 1000)
               .verifyComplete();
    }

    @Test
    void testUpdatePlayer_whenNotExists() {
        Player updatedPlayer = new Player(1L, "Alfonso", 1000);
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(playerServiceMock.updatePlayer(1L, updatedPlayer))
                .expectError(PlayerNotFoundException.class)
                .verify();
    }

    @Test
    void testDeletePlayer_whenExists() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.just(player));
        when(playerRepositoryMock.deleteById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(playerServiceMock.deletePlayerById(1L))
                .verifyComplete();
    }

    @Test
    void testDeletePlayer_whenNotExists() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(playerServiceMock.deletePlayerById(1L))
                .expectError(PlayerNotFoundException.class)
                .verify();
    }
}
