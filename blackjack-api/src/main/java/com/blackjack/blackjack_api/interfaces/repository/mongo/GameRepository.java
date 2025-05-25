package com.blackjack.blackjack_api.interfaces.repository.mongo;

import com.blackjack.blackjack_api.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
}
