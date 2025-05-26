package com.blackjack.blackjack_api.dto;

import com.blackjack.blackjack_api.enums.GameStatus;
import com.blackjack.blackjack_api.model.Game;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    public static GameResponseDTO toDto(Game game, PlayerDTO playerDTO) {
        List<CardDTO> playerCards = game.getPlayerHand()
                .getCards()
                .stream()
                .map(c -> new CardDTO(c.getSuit(), c.getRank()))
                .collect(Collectors.toList());

        List<CardDTO> dealerCards = game.getDealerHand()
                .getCards()
                .stream()
                .map(c -> new CardDTO(c.getSuit(), c.getRank()))
                .collect(Collectors.toList());

        if (game.isPlayerTurn() && game.getStatus() == GameStatus.IN_PROGRESS) {
            dealerCards = dealerCards.subList(0, 1);
        }

        return new GameResponseDTO(
                game.getId(),
                playerDTO,
                playerCards,
                dealerCards,
                game.getStatus(),
                game.isPlayerTurn(),
                game.getDeck().size()
        );
    }
}