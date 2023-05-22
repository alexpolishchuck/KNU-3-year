package com.example.demo.banks;

import com.example.demo.cards.Card;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Bank")
@Table(name = "banks", schema = "root")
public class Bank implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String number;

    @Column(name = "city")
    private String city;

    @OneToMany(targetEntity = Card.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_name", referencedColumnName = "name")
    private List<Card> cards;
}
