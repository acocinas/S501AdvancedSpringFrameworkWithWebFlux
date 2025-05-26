package com.blackjack.blackjack_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private Long playerId;
    private String playerName;
    private long totalGames;
    private long wins;
    private long losses;
    private long draws;
    private double winRate;
}