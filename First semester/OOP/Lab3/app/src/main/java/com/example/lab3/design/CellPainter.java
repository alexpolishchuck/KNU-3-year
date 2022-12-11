package com.example.lab3.design;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;


import com.example.lab3.game_source.Cell;
import com.example.lab3.game_source.figures.Figure;

public class CellPainter {
   public CellPainter(Paint white, Paint black, Paint white_player, Paint black_player,Paint cellHighlighter)
   {
       white_paint = white;
       black_paint = black;
       this.white_player = white_player;
       this.black_player = black_player;
       this.cellHighlighter = cellHighlighter;
   }
   public void setSize(float sizeX, float sizeY)
   {
       if(sizeX <=0 || sizeY <=0)
           throw new IllegalArgumentException("CellPainter. Illegal size of cell.");
       cellSizeX = sizeX;
       cellSizeY = sizeY;
   }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public void paint_cell(Cell cell, Canvas canvas)
   {
        if(cell == null  || canvas == null || cellSizeY <=0 || cellSizeX <=0)
            throw new IllegalArgumentException("Cell painter. Some of the objects passed is null.");
       Paint curpaint = null;
       if(cell.isHighlighted())
           curpaint = cellHighlighter;
           else
       if(cell.getCell_color() == GAME_COLOR.WHITE )
         curpaint =  white_paint;
       else
        curpaint = black_paint;
       float x = cell.getPosition().y * cellSizeX + margin;
       float y = cell.getPosition().x * cellSizeY + margin; //in cell, x and y are swapped, first coordinate is for rows in
                                   //in a table
        canvas.drawRect(x,y,x + cellSizeX, y + cellSizeY,curpaint);

   }
    public void paint_figure(Figure figure,Canvas canvas, Point pos)
    {
        if(canvas == null || cellSizeY <=0 || cellSizeX <=0)
            throw new IllegalArgumentException("Cell painter. Some of the objects passed is null.");
        float radius =0;
        if(cellSizeX > cellSizeY)
            radius = cellSizeY/2;
        else
            radius = cellSizeX/2;
        float x = pos.y * cellSizeX + margin;
        float y = pos.x * cellSizeY + margin;
        if(figure.getPlayer_color() == GAME_COLOR.WHITE)
            canvas.drawCircle(x+cellSizeX/2, y + cellSizeY/2,radius,white_player);
        else
            canvas.drawCircle(x+cellSizeX/2, y + cellSizeY/2,radius,black_player);
    }

    public float getCellSizeY() {
        return cellSizeY;
    }

    public float getCellSizeX() {
        return cellSizeX;
    }

    private float cellSizeX;
    private float cellSizeY;
    private float margin;
    private Paint white_paint;
    private Paint black_paint;
    private Paint white_player;
    private Paint black_player;
    private Paint cellHighlighter;
}
