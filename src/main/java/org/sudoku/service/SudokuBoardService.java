package org.sudoku.service;

import org.sudoku.constants.CommonConstants;
import org.sudoku.model.MoveResult;
import org.sudoku.model.Position;
import org.sudoku.model.ValidationResult;
import org.sudoku.util.BoardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuBoardService {
    private final int[][] currentGrid;
    private final int[][] solutionGrid;
    private final boolean[][] fixedCells;

    public SudokuBoardService(int[][] puzzle, int[][] solution) {
        this.currentGrid = BoardUtils.copyGrid(puzzle);
        this.solutionGrid = BoardUtils.copyGrid(solution);
        this.fixedCells = BoardUtils.buildFixedCells(puzzle);
    }

    public MoveResult placeValue(Position position, int value) {
        if (isFixedCell(position)) {
            return MoveResult.failure("Invalid move. " + position + " is pre-filled.");
        }

        if (!BoardUtils.isValueInRange(value)) {
            return MoveResult.failure(CommonConstants.VALUE_OUT_OF_RANGE_MESSAGE);
        }

        currentGrid[position.getRow()][position.getColumn()] = value;
        return MoveResult.success(CommonConstants.MOVE_ACCEPTED);
    }

    public MoveResult clearCell(Position position) {
        if (isFixedCell(position)) {
            return MoveResult.failure("Invalid move. " + position + " is pre-filled.");
        }

        currentGrid[position.getRow()][position.getColumn()] = CommonConstants.EMPTY_CELL;
        return MoveResult.success(CommonConstants.CELL_CLEARED);
    }

    public Position revealHint(Random random) {
        List<Position> emptyPositions = getEmptyPositions();

        if (emptyPositions.isEmpty()) {
            return null;
        }

        Position randomPosition = emptyPositions.get(random.nextInt(emptyPositions.size()));
        currentGrid[randomPosition.getRow()][randomPosition.getColumn()] =
                solutionGrid[randomPosition.getRow()][randomPosition.getColumn()];

        return randomPosition;
    }

    public int getSolutionValue(Position position) {
        return solutionGrid[position.getRow()][position.getColumn()];
    }

    public boolean isSolved() {
        for (int row = 0; row < CommonConstants.GRID_SIZE; row++) {
            for (int column = 0; column < CommonConstants.GRID_SIZE; column++) {
                if (currentGrid[row][column] != solutionGrid[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    public ValidationResult validateBoard() {
        ValidationResult rowValidation = validateRows();
        if (!rowValidation.isValid()) {
            return rowValidation;
        }

        ValidationResult columnValidation = validateColumns();
        if (!columnValidation.isValid()) {
            return columnValidation;
        }

        ValidationResult subgridValidation = validateSubgrids();
        if (!subgridValidation.isValid()) {
            return subgridValidation;
        }

        return ValidationResult.valid(CommonConstants.NO_RULE_VIOLATIONS_DETECTED);
    }

    public void printBoard() {
        System.out.println("    1 2 3 4 5 6 7 8 9");

        for (int row = 0; row < CommonConstants.GRID_SIZE; row++) {
            System.out.print("  " + (char) ('A' + row) + " ");

            for (int column = 0; column < CommonConstants.GRID_SIZE; column++) {
                int value = currentGrid[row][column];

                if (value == CommonConstants.EMPTY_CELL) {
                    System.out.print(CommonConstants.UNDERSCORE);
                } else {
                    System.out.print(value);
                }

                if (column < CommonConstants.GRID_SIZE - 1) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }

    private boolean isFixedCell(Position position) {
        return fixedCells[position.getRow()][position.getColumn()];
    }

    private List<Position> getEmptyPositions() {
        List<Position> emptyPositions = new ArrayList<>();

        for (int row = 0; row < CommonConstants.GRID_SIZE; row++) {
            for (int column = 0; column < CommonConstants.GRID_SIZE; column++) {
                if (currentGrid[row][column] == CommonConstants.EMPTY_CELL) {
                    emptyPositions.add(new Position(row, column));
                }
            }
        }

        return emptyPositions;
    }

    private ValidationResult validateRows() {
        for (int row = 0; row < CommonConstants.GRID_SIZE; row++) {
            boolean[] seen = new boolean[CommonConstants.MAX_VALUE + 1];

            for (int column = 0; column < CommonConstants.GRID_SIZE; column++) {
                int value = currentGrid[row][column];

                if (value == CommonConstants.EMPTY_CELL) {
                    continue;
                }

                if (seen[value]) {
                    char rowLabel = (char) ('A' + row);
                    return ValidationResult.invalid("Number " + value + " already exists in Row " + rowLabel + ".");
                }

                seen[value] = true;
            }
        }

        return ValidationResult.valid("");
    }

    private ValidationResult validateColumns() {
        for (int column = 0; column < CommonConstants.GRID_SIZE; column++) {
            boolean[] seen = new boolean[CommonConstants.MAX_VALUE + 1];

            for (int row = 0; row < CommonConstants.GRID_SIZE; row++) {
                int value = currentGrid[row][column];

                if (value == CommonConstants.EMPTY_CELL) {
                    continue;
                }

                if (seen[value]) {
                    return ValidationResult.invalid("Number " + value + " already exists in Column " + (column + 1) + ".");
                }

                seen[value] = true;
            }
        }

        return ValidationResult.valid("");
    }

    private ValidationResult validateSubgrids() {
        for (int startRow = 0; startRow < CommonConstants.GRID_SIZE; startRow += CommonConstants.SUBGRID_SIZE) {
            for (int startColumn = 0; startColumn < CommonConstants.GRID_SIZE; startColumn += CommonConstants.SUBGRID_SIZE) {
                boolean[] seen = new boolean[CommonConstants.MAX_VALUE + 1];

                for (int row = startRow; row < startRow + CommonConstants.SUBGRID_SIZE; row++) {
                    for (int column = startColumn; column < startColumn + CommonConstants.SUBGRID_SIZE; column++) {
                        int value = currentGrid[row][column];

                        if (value == CommonConstants.EMPTY_CELL) {
                            continue;
                        }

                        if (seen[value]) {
                            return ValidationResult.invalid(
                                    "Number " + value + " already exists in the same 3×3 subgrid."
                            );
                        }

                        seen[value] = true;
                    }
                }
            }
        }

        return ValidationResult.valid("");
    }
}
