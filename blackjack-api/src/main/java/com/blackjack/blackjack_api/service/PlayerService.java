package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.interfaces.repository.mysql.PlayerRepository;
import com.blackjack.blackjack_api.model.Player;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Mono<Player> createPlayer(Player player) {
        player.setId(null);
        return playerRepository.save(player);
    }

    public Mono<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Mono<Player> updatePlayer(Long id, Player player) {
        return playerRepository.findById(id)
                .flatMap(existingPlayer -> {
                    existingPlayer.setName(player.getName());
                    existingPlayer.setScore(player.getScore());
                    return playerRepository.save(existingPlayer);
                });
    }

    public Mono<Void> deletePlayer(Long id) {
        return playerRepository.deleteById(id);
    }
}
