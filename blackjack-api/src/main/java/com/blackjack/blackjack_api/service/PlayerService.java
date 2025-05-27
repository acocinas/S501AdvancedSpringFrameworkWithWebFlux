package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.exception.PlayerNotFoundException;
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
        if (player.getBalance() == null) player.setBalance(100);
        return playerRepository.save(player);
    }

    public Mono<Player> getPlayerById(Long id) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(id)));
    }

    public Mono<Player> updatePlayer(Long id, Player player) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(id)))
                .flatMap(existingPlayer -> {
                    existingPlayer.setName(player.getName());
                    existingPlayer.setBalance(player.getBalance());
                    return playerRepository.save(existingPlayer);
                });
    }

    public Mono<Void> deletePlayerById(Long id) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(id)))
                .flatMap(existingPlayer -> playerRepository.deleteById(id));
    }
}