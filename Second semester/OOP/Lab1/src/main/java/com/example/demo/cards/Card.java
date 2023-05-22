package com.example.demo.cards;

import com.example.demo.users.Person;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Card {

    private int id;

    private String number;

    private String owners_name;

    private boolean is_blocked;
}
