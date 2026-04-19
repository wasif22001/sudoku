package org.sudoku.service;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.sudoku.constants.CommonConstants;
import org.sudoku.model.MoveResult;
import org.sudoku.model.Position;
import org.sudoku.model.ValidationResult;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardServiceTest {

    private SudokuBoardService sudokuBoardService;

    @BeforeEach
    void setUp() {
        sudokuBoardService = new SudokuBoardService(
                CommonConstants.DEFAULT_PUZZLE,
                CommonConstants.DEFAULT_SOLUTION
        );
    }

    @Test
    void shouldPlaceValueInEditableCell() {
        Position position = new Position(0, 2);
        MoveResult result = sudokuBoardService.placeValue(position, 4);

        assertTrue(result.isSuccess());
        assertEquals("Move accepted.", result.getMessage());
    }

    @Test
    void shouldNotPlaceValueInPreFilledCell() {
        Position position = new Position(0, 0);
        MoveResult result = sudokuBoardService.placeValue(position, 6);

        assertFalse(result.isSuccess());
        assertEquals("Invalid move. A1 is pre-filled.", result.getMessage());
    }

    @Test
    void shouldNotPlaceValueWhenOutOfRangeLow() {
        Position position = new Position(0, 2);
        MoveResult result = sudokuBoardService.placeValue(position, 0);

        assertFalse(result.isSuccess());
        assertEquals(CommonConstants.VALUE_OUT_OF_RANGE_MESSAGE, result.getMessage());
    }

    @Test
    void shouldNotPlaceValueWhenOutOfRangeHigh() {
        Position position = new Position(0, 2);
        MoveResult result = sudokuBoardService.placeValue(position, 10);

        assertFalse(result.isSuccess());
        assertEquals(CommonConstants.VALUE_OUT_OF_RANGE_MESSAGE, result.getMessage());
    }

    @Test
    void shouldClearEditableCell() {
        Position position = new Position(0, 2);
        sudokuBoardService.placeValue(position, 4);

        MoveResult result = sudokuBoardService.clearCell(position);

        assertTrue(result.isSuccess());
        assertEquals("Cell cleared.", result.getMessage());
    }

    @Test
    void shouldNotClearPreFilledCell() {
        Position position = new Position(0, 0);
        MoveResult result = sudokuBoardService.clearCell(position);

        assertFalse(result.isSuccess());
        assertEquals("Invalid move. A1 is pre-filled.", result.getMessage());
    }

    @Test
    void shouldRevealHintForEmptyCell() {
        Position hintPosition = sudokuBoardService.revealHint(new Random(1));

        assertNotNull(hintPosition);

        int actualValue = sudokuBoardService.getSolutionValue(hintPosition);
        assertEquals(actualValue, sudokuBoardService.getSolutionValue(hintPosition));
    }

    @Test
    void shouldReturnNullWhenHintRequestedOnSolvedBoard() {
        SudokuBoardService solvedBoard = new SudokuBoardService(
                CommonConstants.DEFAULT_SOLUTION,
                CommonConstants.DEFAULT_SOLUTION
        );

        Position hintPosition = solvedBoard.revealHint(new Random());

        assertNull(hintPosition);
    }

    @Test
    void shouldReturnFalseWhenBoardIsNotSolved() {
        assertFalse(sudokuBoardService.isSolved());
    }

    @Test
    void shouldReturnTrueWhenBoardIsSolved() {
        SudokuBoardService solvedBoard = new SudokuBoardService(
                CommonConstants.DEFAULT_SOLUTION,
                CommonConstants.DEFAULT_SOLUTION
        );

        assertTrue(solvedBoard.isSolved());
    }

    @Test
    void shouldValidateBoardWhenThereAreNoViolations() {
        ValidationResult result = sudokuBoardService.validateBoard();

        assertTrue(result.isValid());
        assertEquals("No rule violations detected.", result.getMessage());
    }

    @Test
    void shouldDetectDuplicateInRow() {
        sudokuBoardService.placeValue(new Position(0, 2), 3);

        ValidationResult result = sudokuBoardService.validateBoard();

        assertFalse(result.isValid());
        assertEquals("Number 3 already exists in Row A.", result.getMessage());
    }

    @Test
    void shouldDetectDuplicateInColumn() {
        sudokuBoardService.placeValue(new Position(2, 0), 5);

        ValidationResult result = sudokuBoardService.validateBoard();

        assertFalse(result.isValid());
        assertEquals("Number 5 already exists in Column 1.", result.getMessage());
    }

    @Test
    void shouldDetectDuplicateInSubgrid() {
        sudokuBoardService.placeValue(new Position(1, 2), 8);

        ValidationResult result = sudokuBoardService.validateBoard();

        assertFalse(result.isValid());
        assertEquals("Number 8 already exists in Column 3.", result.getMessage());
    }

}
