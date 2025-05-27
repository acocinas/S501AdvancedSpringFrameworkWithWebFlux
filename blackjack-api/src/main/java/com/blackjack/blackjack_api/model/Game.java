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
        this.id = UUID.randomUUID().toString();
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();

        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        this.status = GameStatus.IN_PROGRESS;
        this.playerTurn = true;
        this.bet = 0;
    }

    public Game(Long playerId, int initialBet) {
        this();
        this.playerId = playerId;
        if(initialBet <= 0) {
            throw new InvalidActionException("La apuesta no puede ser 0");
        }
        this.bet = initialBet;
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
            status = GameStatus.DEALER_BUSTED;
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