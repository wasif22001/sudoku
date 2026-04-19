package org.sudoku;

import org.sudoku.service.SudokuGameService;

public class SudokuApplication {
    public static void main(String[] args) {
        SudokuGameService sudokuGameService = new SudokuGameService();
        sudokuGameService.start();
    }
}