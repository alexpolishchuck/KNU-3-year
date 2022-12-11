package com.example.lab3.game_source.figures;

import android.graphics.Point;
import android.util.Pair;

import com.example.lab3.game_source.Board;

import java.util.ArrayList;
import java.util.HashMap;

public interface Strategy {

     ArrayList<ArrayList<Point>> find_paths(Board board, Point currently_selected);
}
