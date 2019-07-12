package com.practice.blueTeam.algo;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(3);
        int[] data = puzzle.getTilesArray();
        for (int i: data)
            System.out.print(i);
        System.out.println();

        System.out.println(puzzle.numberMisplacedTiles());

        List<Puzzle> solution = puzzle.dijkstraSolve();
        for (Puzzle p: solution) {
            int[] dataa = p.getTilesArray();
            for (int i: dataa)
                System.out.print(i);
            System.out.println();
        }

    }
}
