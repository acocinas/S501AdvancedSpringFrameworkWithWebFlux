package com.blackjack.blackjack_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameRequestDTO {

    @NotNull(message = "Se necestia el Id del jugador")
    private Long playerId;

    @Positive(message = "La apuesta debe ser mayor a 0")
    private int bet;
}