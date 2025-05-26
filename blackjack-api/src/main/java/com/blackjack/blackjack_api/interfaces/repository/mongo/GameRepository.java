package com.blackjack.blackjack_api.interfaces.repository.mongo;

import com.blackjack.blackjack_api.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Flux<Game> findByPlayerId(Long playerId);
}
