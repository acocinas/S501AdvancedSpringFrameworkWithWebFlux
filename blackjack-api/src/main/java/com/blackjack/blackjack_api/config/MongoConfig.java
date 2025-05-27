package com.blackjack.blackjack_api.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUrl;

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(mongoUrl);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient client) {
        return new ReactiveMongoTemplate(client, "blackjack_db");
}
}