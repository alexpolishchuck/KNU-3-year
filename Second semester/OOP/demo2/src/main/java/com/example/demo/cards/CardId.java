package com.example.demo.cards;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class CardId implements Serializable {

    public CardId(int id, String number)
    {
        this.id = id;
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private int id;
    private String number;
}
