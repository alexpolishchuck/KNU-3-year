package com.example.demo.cards;

import com.example.demo.banks.Bank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Card")
@Table(name = "cards", schema = "root")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    private String number;

    @Column(name = "owners_name")
    private String owners_name;

    @Column(name = "bank_name")
    private String bank_name;

    @JsonProperty("is_blocked")
    @Column(name = "is_blocked")
    private boolean is_blocked;

    @ManyToOne
    @JoinColumn(name="bank_name", referencedColumnName = "name", insertable=false, updatable=false)
    private Bank bank;
}
