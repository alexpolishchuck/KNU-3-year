package com.example.lab3.game_source.figures;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.util.Pair;

import com.example.lab3.game_source.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class RegularFigureStrategy implements Strategy{


    @SuppressLint("SuspiciousIndentation")
    @Override
    public  ArrayList<ArrayList<Point>> find_paths(Board board, Point currently_selected) {
        ArrayList<ArrayList<Point>> paths = new ArrayList<>();
        stack = new Stack<>();
        stack.add(currently_selected);

        if( currently_selected.y + 1 < board.getSIZE() &&
                board.getCell(currently_selected.x - 1, currently_selected.y + 1).getFigure() == null)
        {
            paths.add(new ArrayList<Point>(Arrays.asList(currently_selected,
                new Point(currently_selected.x - 1, currently_selected.y + 1))));
        }
        if( currently_selected.y - 1 >=0 &&
                board.getCell(currently_selected.x - 1, currently_selected.y - 1).getFigure() == null)
        {
            paths.add(new ArrayList<Point>(Arrays.asList(currently_selected,
                            new Point(currently_selected.x - 1, currently_selected.y - 1))));
        }

        // searching for paths which include beating figures
    //    ArrayList<Point> path = new ArrayList<>();
        beaten_figures = new boolean[board.getSIZE()][board.getSIZE()];
        should_be_added = false;

        while(!stack.empty())
        {
            Point current_pos = stack.peek();

            boolean has_beaten_figure = false;
            if(current_pos.x + 1 < board.getSIZE() &&
                     current_pos.x + 2 < board.getSIZE() &&
                     current_pos.y + 1 < board.getSIZE() &&
                     current_pos.y + 2 < board.getSIZE()&&
                     board.getCell(current_pos.x + 1, current_pos.y + 1).getFigure() != null &&
                    ( board.getCell(current_pos.x + 2, current_pos.y + 2).getFigure() == null ||
                            board.getCell(current_pos.x + 2, current_pos.y + 2).
                                    getPosition().equals(currently_selected)) &&
                     !beaten_figures[current_pos.x + 1][current_pos.y + 1])
                {
                    beaten_figures[current_pos.x + 1][current_pos.y + 1] = true;
                    has_beaten_figure = true;
                    should_be_added = true;
                    stack.add(new Point(current_pos.x + 2, current_pos.y + 2));

                }else
            if(current_pos.x - 1 >= 0 &&
                    current_pos.x - 2 >= 0 &&
                    current_pos.y + 1 < board.getSIZE() &&
                    current_pos.y + 2 < board.getSIZE() &&
                    board.getCell(current_pos.x - 1, current_pos.y + 1).getFigure() != null &&
                   ( board.getCell(current_pos.x - 2, current_pos.y + 2).getFigure() == null ||
                           board.getCell(current_pos.x - 2, current_pos.y + 2).
                                   getPosition().equals(currently_selected)) &&
                    !beaten_figures[current_pos.x - 1][current_pos.y + 1])
                {
                    beaten_figures[current_pos.x - 1][current_pos.y + 1] = true;
                    has_beaten_figure = true;
                    should_be_added = true;
                    stack.add(new Point(current_pos.x - 2, current_pos.y + 2));

                }else
            if(current_pos.x - 1 >= 0 &&
                    current_pos.x - 2 >= 0 &&
                    current_pos.y - 1 >=0 &&
                    current_pos.y - 2 >=0 &&
                    board.getCell(current_pos.x - 1, current_pos.y - 1).getFigure() != null &&
                    (board.getCell(current_pos.x - 2, current_pos.y - 2).getFigure() == null||
                            board.getCell(current_pos.x - 2, current_pos.y - 2).
                                    getPosition().equals(currently_selected)) &&
                    !beaten_figures[current_pos.x - 1][current_pos.y - 1])
                {
                    beaten_figures[current_pos.x - 1][current_pos.y - 1] = true;
                    has_beaten_figure = true;
                    should_be_added = true;
                    stack.add(new Point(current_pos.x - 2, current_pos.y - 2));

                }
            else
            if(current_pos.x + 1 < board.getSIZE() &&
                    current_pos.x + 2 < board.getSIZE() &&
                    current_pos.y - 1 >=0 &&
                    current_pos.y - 2 >=0 &&
                    board.getCell(current_pos.x + 1, current_pos.y - 1).getFigure() != null &&
                    (board.getCell(current_pos.x + 2, current_pos.y - 2).getFigure() == null ||
                            board.getCell(current_pos.x + 2, current_pos.y - 2).
                                    getPosition().equals(currently_selected)) &&
                    !beaten_figures[current_pos.x + 1][current_pos.y - 1])
                {
                    beaten_figures[current_pos.x + 1][current_pos.y - 1] = true;
                    has_beaten_figure = true;
                    should_be_added = true;
                    stack.add(new Point(current_pos.x + 2, current_pos.y - 2));
                }

            if(has_beaten_figure == false)
            {
                if(should_be_added)
                paths.add(new ArrayList<>(stack));
                should_be_added = false;
                stack.pop();
            }



        }

        return paths;
    }


    private  boolean[][] beaten_figures;
    private boolean should_be_added = false;
    private Stack<Point> stack;
}
