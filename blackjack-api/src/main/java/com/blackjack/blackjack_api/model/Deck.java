package com.blackjack.blackjack_api.model;

import com.blackjack.blackjack_api.enums.Rank;
import com.blackjack.blackjack_api.enums.Suit;
import lombok.Getter;
import java.util.*;

@Getter
public class Deck {

    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No more cards in the deck.");
        } else {
            return cards.remove(0);
        }
        }
        public int size() {
            return cards.size();
        }
        public boolean isEmpty() {
            return cards.isEmpty();
        }
    }
