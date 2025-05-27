package com.blackjack.blackjack_api.model;

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

    @Column("balance")
    private Integer balance;
}