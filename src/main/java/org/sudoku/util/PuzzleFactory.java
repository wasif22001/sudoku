package org.sudoku.util;

import org.sudoku.constants.CommonConstants;
import org.sudoku.service.SudokuBoardService;

public final class PuzzleFactory {

    private PuzzleFactory() {
    }

    public static SudokuBoardService createDefaultBoard() {
        return new SudokuBoardService(
                CommonConstants.DEFAULT_PUZZLE,
                CommonConstants.DEFAULT_SOLUTION
        );
    }
}
