package com.example.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lab3.GameManager.GameManager;
import com.example.lab3.design.CellPainter;
import com.example.lab3.design.GAME_COLOR;
import com.example.lab3.game_source.Board;
import com.example.lab3.game_source.Cell;
import com.example.lab3.game_source.figures.Figure;

import java.util.ArrayList;
import java.util.EnumMap;

public class GameView extends View {

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Paint black = new Paint();
        black.setColor(Color.parseColor("#A52A2A"));
        Paint white = new Paint();
        white.setColor(Color.parseColor("#FAEBD7"));
        Paint black_player = new Paint();
        black_player.setColor(Color.parseColor("#808080"));
        Paint white_player = new Paint();
        white_player.setColor(Color.parseColor("#FFFAF0"));
        Paint highlighter = new Paint();
        highlighter.setColor(Color.parseColor("#D69F13"));
        names_of_players = new EnumMap<GAME_COLOR, String>(GAME_COLOR.class);
        names_of_players.put(GAME_COLOR.WHITE, "White");
        names_of_players.put(GAME_COLOR.BLACK, "Black");

        cellPainter = new CellPainter(white, black, white_player,black_player,highlighter);
        gameManager = new GameManager(new Board());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
           float selectedX = event.getX() - margin;
           float selectedY = event.getY() - margin;
           Point tablePos = new Point((int)(selectedY/cellPainter.getCellSizeY()),
                                      (int)(selectedX/cellPainter.getCellSizeX()));
           if(tablePos.y < 0 ||  tablePos.y >=gameManager.getBoard().getSIZE() ||
                   tablePos.x <0 || tablePos.x >=gameManager.getBoard().getSIZE())
               return super.onTouchEvent(event);
           gameManager.select_or_move(tablePos);
           invalidate();

        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        int width = getWidth();
        int height = getHeight();
        int SIZE = gameManager.getBoard().getSIZE();
        float Xcell_size = width/(SIZE+1);
        float Ycell_size = height/(SIZE+1);
        if( width < height)
            margin = (width - Xcell_size * SIZE)/2;
        else
            margin = (height - Ycell_size * SIZE)/2;
        cellPainter.setMargin(margin);
        cellPainter.setSize(Xcell_size,Ycell_size);

        draw_board(canvas);
    }
    private void draw_board(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        int SIZE = gameManager.getBoard().getSIZE();
        for(int i=0; i<SIZE; i++)
            for(int j=0; j< SIZE; j++)
            {
                cellPainter.paint_cell(gameManager.getBoard().getCell(i, j),canvas );
                if(gameManager.getBoard().getCell(i, j).getFigure() !=null)
                    cellPainter.paint_figure(gameManager.getBoard().getCell(i, j).getFigure(), canvas,gameManager.getBoard().getCell(i, j).getPosition());
            }
    }

    //private Board board;
    private CellPainter cellPainter;
    private float margin;
    private GameManager gameManager;
    private EnumMap<GAME_COLOR,String> names_of_players;
}
