package com.example.demo.cards;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardId implements Serializable {

    public String getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    private int id;
    private String number;
}
