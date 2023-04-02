package com.example.demo.cards;

import com.example.demo.users.Person;
import jakarta.persistence.*;

@Entity(name = "Card")
@Table(name = "cards", schema = "root")
@IdClass(CardId.class)
public class Card {
    public int getOwners_id() {
        return owners_id;
    }

    public void setOwners_id(int owners_id) {
        this.owners_id = owners_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Id
    @Column(name = "number")
    private String number;
    @Column(name = "owners_id")
    private int owners_id;

    @ManyToOne(targetEntity = Person.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "owners_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Person person;
}
