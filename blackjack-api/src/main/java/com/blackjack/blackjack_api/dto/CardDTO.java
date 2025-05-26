package com.blackjack.blackjack_api.dto;

import com.blackjack.blackjack_api.enums.Rank;
import com.blackjack.blackjack_api.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Suit suit;
    private Rank rank;
}