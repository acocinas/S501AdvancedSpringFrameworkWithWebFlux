package com.blackjack.blackjack_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "com.blackjack.blackjack_api.interfaces.repository.mysql")
@EnableReactiveMongoRepositories(basePackages = "com.blackjack.blackjack_api.interfaces.repository.mongo")
public class BlackjackApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackjackApiApplication.class, args);
	}

}
