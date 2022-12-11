package com.example.lab3.game_source.figures;

import android.graphics.Point;

import com.example.lab3.design.GAME_COLOR;

public class Figure {
    public Figure(GAME_COLOR player_color, Strategy strategy)
    {
        if( player_color == null)
            throw new IllegalArgumentException("Figure. Illegal arguments");
        this.player_color = player_color;
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy()
    {
        return this.strategy;
    }

    public GAME_COLOR getPlayer_color() {
        return player_color;
    }
    private GAME_COLOR player_color;
    private Strategy strategy;
}
