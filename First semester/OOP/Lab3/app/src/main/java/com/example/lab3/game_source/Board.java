package com.example.lab3.game_source;

import android.graphics.Point;

import com.example.lab3.design.GAME_COLOR;
import com.example.lab3.game_source.figures.Figure;
import com.example.lab3.game_source.figures.RegularFigureStrategy;

public class Board {

    public Board()
    {
        createBoard();
    }
    public int getSIZE() {
        return SIZE;
    }
    public Cell getCell(int x, int y)
    {
        if(x < 0 || y<0 || x>= SIZE || y>=SIZE)
            throw new IllegalArgumentException("Board. Out of bounds. X or Y.");

        return cells[x][y];
    }
    public void setCell(int x, int y, Cell cell)
    {
        if(x < 0 || y<0 || x>= SIZE || y>=SIZE)
            throw new IllegalArgumentException("Board. Out of bounds. X or Y.");
        cells[x][y] = cell;
    }

    private void createBoard()
    {
        GAME_COLOR current_color = GAME_COLOR.WHITE;

        cells = new Cell[SIZE][SIZE];
        for(int i=0; i<SIZE; i++)
        {
            if(i % 2 ==0)
                current_color = GAME_COLOR.WHITE;
            else
                current_color = GAME_COLOR.BLACK;

            for(int j=0; j<SIZE; j++)
            {
                cells[i][j] = new Cell(new Point(i,j),current_color);
                if(current_color == GAME_COLOR.BLACK)
                {
                    if(i < SIZE/2 - 1)
                        cells[i][j].setFigure(new Figure(GAME_COLOR.BLACK, new RegularFigureStrategy()));
                    else if(i > SIZE/2 )
                        cells[i][j].setFigure(new Figure(GAME_COLOR.WHITE, new RegularFigureStrategy()));
                }
                if(current_color == GAME_COLOR.BLACK)
                    current_color = GAME_COLOR.WHITE;
                else
                    current_color = GAME_COLOR.BLACK;
            }
        }
    }



    private Cell[][] cells;
    private int SIZE = 8;

}
