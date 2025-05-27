package com.blackjack.blackjack_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("players")

public class Player {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Schema(description = "Points available to bet", defaultValue = "100")
    @Column("balance")
    @JsonProperty("score")
    private Integer balance = 100;
}