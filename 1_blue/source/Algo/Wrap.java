package com.practice.blueTeam.algo;

import java.util.List;
import java.util.Stack;

public class Wrap extends Puzzle {

    private static Puzzle puzzle = new Puzzle();
    private static Stack<Puzzle> states;
    private static List<int[]> solution;

    private static final int numOfShuffles = 5;

    public static int[] wrapGetTilesArray(Puzzle puzzle) {
        int[] out = new int[16];
        int c = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                out[c] = puzzle.getTiles()[i][j];
                c++;
            }
        }
        return out;
    }

    public static int[] wrapRandomizePuzzle() {
        Puzzle temp = new Puzzle(numOfShuffles);
        puzzle.setTiles(temp.getTiles());
        states.push(puzzle);
        return wrapGetTilesArray(puzzle);
    }

    public int[] wrapMoveTile (int numOfTile) {
        puzzle.move(whereIs(numOfTile));
        states.push(puzzle);
        return wrapGetTilesArray(puzzle);
    }

}
