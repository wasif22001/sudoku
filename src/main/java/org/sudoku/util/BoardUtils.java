package org.sudoku.util;

import static org.sudoku.constants.CommonConstants.*;

public final class BoardUtils {


    private BoardUtils() {
    }

    public static int[][] copyGrid(int[][] source) {
        int[][] copy = new int[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            System.arraycopy(source[row], 0, copy[row], 0, GRID_SIZE);
        }

        return copy;
    }

    public static boolean[][] buildFixedCells(int[][] puzzle) {
        boolean[][] fixedCells = new boolean[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                fixedCells[row][column] = puzzle[row][column] != EMPTY_CELL;
            }
        }

        return fixedCells;
    }

    public static boolean isValueInRange(int value) {
        return value >= MIN_VALUE && value <= MAX_VALUE;
    }
}
