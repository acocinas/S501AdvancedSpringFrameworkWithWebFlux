package com.blackjack.blackjack_api.service;

import com.blackjack.blackjack_api.enums.GameStatus;
import com.blackjack.blackjack_api.model.Deck;
import com.blackjack.blackjack_api.model.Hand;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Game {

    private final UUID  id;
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private GameStatus status;
    private boolean playerTurn;

    public Game() {
        this.id = UUID.randomUUID();
        this.deck = new Deck();

        this.playerHand = new Hand();
        this.dealerHand = new Hand();

        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        this.status = GameStatus.IN_PROGRESS;
        this.playerTurn = true;
    }

    private void ensurePlayerCanPlay() {
        if (status != GameStatus.IN_PROGRESS || !playerTurn) {
            throw new IllegalStateException(
                    "Cannot play now: status=" + status + ", playerTurn=" + playerTurn
            );
        }
    }
    public void playerHit() {
        ensurePlayerCanPlay();
        if(!playerHand.canHit()){
            throw new IllegalStateException("Player cannot hit" + playerHand);
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
}
