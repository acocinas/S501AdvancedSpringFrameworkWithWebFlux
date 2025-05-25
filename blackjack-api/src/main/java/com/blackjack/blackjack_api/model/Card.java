package com.blackjack.blackjack_api.model;

import com.blackjack.blackjack_api.enums.Rank;
import com.blackjack.blackjack_api.enums.Suit;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Suit suit;
    private Rank rank;

    public int getValue() {
        return switch (rank){
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case TEN, JACK, QUEEN, KING -> 10;
            case ACE -> 11;
        };

    }
}
