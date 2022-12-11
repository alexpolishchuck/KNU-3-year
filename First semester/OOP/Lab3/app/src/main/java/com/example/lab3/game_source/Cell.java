package com.example.lab3.game_source;

import android.graphics.Point;

import com.example.lab3.design.GAME_COLOR;
import com.example.lab3.game_source.figures.Figure;

public class Cell {
    public Cell(Point position, GAME_COLOR cellColor)
    {
        this.cellColor = cellColor;
        this.position = position;
        is_highlighted = false;
    }
    public Cell(Point position, GAME_COLOR cellColor, Figure figure)
    {
        this.cellColor = cellColor;
        this.position = position;
        this.figure = figure;
        is_highlighted = false;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public GAME_COLOR getCell_color() {
        return cellColor;
    }

    public Point getPosition() {
        return position;
    }

    public void setIsHighlighted(boolean is_highlighted) {
        this.is_highlighted = is_highlighted;
    }

    public boolean isHighlighted()
    {
        return is_highlighted;
    }

    private Figure figure;
    private GAME_COLOR cellColor;
    private Point position;
    private boolean is_highlighted;
}
