package com.example.lab3.GameManager;

import android.graphics.Point;

import com.example.lab3.design.GAME_COLOR;
import com.example.lab3.game_source.Board;
import com.example.lab3.game_source.Cell;
import com.example.lab3.game_source.figures.Figure;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Stack;

public class GameManager {

    public GameManager(Board board)
    {
        turn_of = GAME_COLOR.WHITE;
        is_cell_activated = false;
        this.board = board;

        number_if_figures = new EnumMap<GAME_COLOR, Integer>(GAME_COLOR.class);
        number_if_figures.put(GAME_COLOR.WHITE,12);
        number_if_figures.put(GAME_COLOR.BLACK,12);

    }

    public void select_or_move(Point pos)
    {
        Cell cell = board.getCell(pos.x, pos.y);

        if(is_cell_activated) {
            is_cell_activated = false;
            setHighlightedPath(false);
            if (!isAbleToMoveTo(pos))
                return;
            else
                moveFigureTo(pos);

        } else
        {
            if(cell.getFigure()==null || cell.getFigure().getPlayer_color() != turn_of)
                return;
            currently_selected = pos;
            is_cell_activated = true;
            current_highlighted_paths = cell.getFigure().getStrategy().find_paths(board,pos);
            setHighlightedPath(true);
        }



    }

    public Point getCurrently_selected() {
        return currently_selected;
    }

    public Board getBoard() {
        return board;
    }

    private void setHighlightedPath(boolean isHighlighted)
    {
        if(current_highlighted_paths !=null)
        {
            int size = current_highlighted_paths.size();
            for(int i=0; i<size; i++)
            {
                ArrayList<Point> path = current_highlighted_paths.get(i);
                board.getCell(path.get(path.size()-1).x, path.get(path.size()-1).y).setIsHighlighted(isHighlighted);
            }
        }
    }

    private void moveFigureTo(Point pos)
    {
        Figure figure = board.getCell(currently_selected.x,currently_selected.y).getFigure();
        board.getCell(currently_selected.x,currently_selected.y).setFigure(null);
        board.getCell(pos.x,pos.y).setFigure(figure);
    }

    private boolean isAbleToMoveTo(Point pos)
    {
        if(current_highlighted_paths !=null)
        {
            int size = current_highlighted_paths.size();
            for(int i=0; i<size; i++)
            {
                ArrayList<Point> path = current_highlighted_paths.get(i);
                int pathSize = path.size();
                if(path.get(pathSize -1).equals(pos))
                {
                    destroyFiguresBetweenSteps(path);
                    return true;
                }
            }
        }
        return false;
    }
    private void destroyFiguresBetweenSteps(ArrayList<Point> path)
    {
        int size = path.size();
        for(int i=1; i<size; i++)
        {
            int xMid = (path.get(i).x + path.get(i-1).x)/2;
            int yMid = (path.get(i).y + path.get(i-1).y)/2;
            board.getCell(xMid,yMid).setFigure(null);
        }
    }
    private boolean is_cell_activated;
    private Point currently_selected;
    private GAME_COLOR turn_of;
    private Board board;
    private ArrayList<ArrayList<Point>> current_highlighted_paths;
    private EnumMap<GAME_COLOR,Integer> number_if_figures;
}
