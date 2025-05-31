package com.blackjack.blackjack_api.model;

import com.blackjack.blackjack_api.enums.GameStatus;
import com.blackjack.blackjack_api.exception.InvalidActionException;
import com.blackjack.blackjack_api.exception.InvalidPlayException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document(collection = "games")
public class Game {

    @Id
    private String id;
    private Long playerId;
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private GameStatus status;
    private boolean playerTurn;
    private int bet;

    public Game() {
        // Constructor vacío necesario para deserialización de MongoDB
    }

    public Game(Long playerId, int initialBet) {
        if(initialBet <= 0) {
            throw new InvalidActionException("La apuesta no puede ser 0");
        }
        this.id = UUID.randomUUID().toString();
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.playerId = playerId;
        this.bet = initialBet;
        this.status = GameStatus.IN_PROGRESS;
        this.playerTurn = true;
    }

    public void dealInitialCards() {
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
    }

    public void checkInitialBlackjack() {
        boolean playerBlackjack = playerHand.isBlackjack();
        boolean dealerBlackjack = dealerHand.isBlackjack();

        if (playerBlackjack){
            this.playerTurn = false;
            if(dealerBlackjack) {
                this.status = GameStatus.DRAW;
            } else {
                this.status = GameStatus.PLAYER_WIN;
            }
        }
    }

    private void ensurePlayerCanPlay() {
        if (status != GameStatus.IN_PROGRESS || !playerTurn) {
            throw new InvalidPlayException(
                    "No puede jugar " + status + ", playerTurn=" + playerTurn
            );
        }
    }
    public void playerHit() {
        ensurePlayerCanPlay();
        if(!playerHand.canHit()){
            throw new InvalidActionException("No puede pedir carta" + playerHand);
        }
        playerHand.addCard(deck.drawCard());
        if(playerHand.isBusted()) {
            status = GameStatus.PLAYER_BUSTED;
            playerTurn = false;
        } else if (playerHand.hasTwentyOne()) {
            playerTurn = false;
            dealerPlay();
        }
    }

    public void playerStand() {
        ensurePlayerCanPlay();
        playerHand.stand();
        playerTurn = false;
        dealerPlay();
    }

    private void dealerPlay() {
        if(status != GameStatus.IN_PROGRESS) return;

        while (dealerHand.calculateValue() < 17) {
            dealerHand.addCard(deck.drawCard());
        }
        if(dealerHand.isBusted()) {
            status = GameStatus.PLAYER_WIN;
            return;
        }
        int dealerValue = dealerHand.calculateValue();
        int playerValue = playerHand.calculateValue();

        if(dealerValue > playerValue) {
            status = GameStatus.DEALER_WIN;
        }else if (dealerValue < playerValue) {
            status = GameStatus.PLAYER_WIN;
        }else {
            status = GameStatus.DRAW;
        }
    }

    public boolean isBlackjack() {
        return playerHand.isBlackjack();
    }
}