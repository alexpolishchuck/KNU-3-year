package com.example.demo.cards;

import com.example.demo.users.Person;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "is_blocked")
    private boolean is_blocked;

    @ManyToOne(targetEntity = Person.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "owners_name", referencedColumnName = "name", insertable=false, updatable=false)
    private Person person;
}
