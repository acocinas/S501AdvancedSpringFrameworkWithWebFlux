package com.blackjack.blackjack_api.interfaces.repository;

import com.blackjack.blackjack_api.model.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {
}
