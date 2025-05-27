package com.blackjack.blackjack_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para el jugador")
public class PlayerDTO {
    @Schema(description = "Id del jugador", example = "1")
    private Long id;

    @Schema(description = "Nombre del jugador", example = "Pepe")
    private String name;

    @Schema(description = "Saldo del jugador", example = "100")
    private Integer balance;
}