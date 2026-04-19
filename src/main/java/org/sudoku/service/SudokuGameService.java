package org.sudoku.service;

import org.sudoku.constants.CommonConstants;
import org.sudoku.model.MoveResult;
import org.sudoku.model.Position;
import org.sudoku.model.ValidationResult;
import org.sudoku.util.CommandParser;
import org.sudoku.util.PuzzleFactory;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class SudokuGameService {

    private final Scanner scanner;
    private final PrintStream out;
    private final Random random;

    public SudokuGameService() {
        this(new Scanner(System.in), System.out, new Random());
    }

    public SudokuGameService(Scanner scanner, PrintStream out, Random random) {
        this.scanner = scanner;
        this.out = out;
        this.random = random;
    }

    public void start() {
        out.println(CommonConstants.WELCOME_TO_SUDOKU);
        out.println();

        boolean playAgain = true;

        while (playAgain) {
            SudokuBoardService board = PuzzleFactory.createDefaultBoard();

            out.println(CommonConstants.IS_YOUR_PUZZLE);
            board.printBoard();

            boolean completed = playSingleGame(board);

            if (!completed) {
                return;
            }

            out.print(CommonConstants.REPLAY_PROMPT);
            String input = scanner.nextLine().trim();
            playAgain = !CommonConstants.QUIT.equalsIgnoreCase(input);
            out.println();
        }

        out.println(CommonConstants.THANKS_FOR_PLAYING_SUDOKU);
    }

    private boolean playSingleGame(SudokuBoardService board) {
        while (true) {
            out.println();
            out.print(CommonConstants.INPUT_PROMPT);

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                out.println(CommonConstants.INVALID_COMMAND);
                continue;
            }

            if (CommonConstants.QUIT.equalsIgnoreCase(input)) {
                out.println(CommonConstants.GOODBYE);
                return false;
            }

            if (CommonConstants.HINT.equalsIgnoreCase(input)) {
                handleHint(board);
                if (board.isSolved()) {
                    out.println(CommonConstants.WIN_MESSAGE);
                    return true;
                }
                continue;
            }

            if (CommonConstants.CHECK.equalsIgnoreCase(input)) {
                ValidationResult result = board.validateBoard();
                out.println(result.getMessage());
                continue;
            }

            handleMove(board, input);

            if (board.isSolved()) {
                out.println(CommonConstants.WIN_MESSAGE);
                return true;
            }
        }
    }

    private void handleHint(SudokuBoardService board) {
        Position hintPosition = board.revealHint(random);

        if (hintPosition == null) {
            out.println(CommonConstants.NO_HINT_AVAILABLE_MESSAGE);
            return;
        }

        out.println("Hint: Cell " + hintPosition + " = " + board.getSolutionValue(hintPosition));
        out.println();
        out.println(CommonConstants.CURRENT_GRID);
        board.printBoard();
    }

    private void handleMove(SudokuBoardService board, String input) {
        String[] parts = input.split("\\s+");

        if (parts.length != 2) {
            out.println(CommonConstants.INVALID_COMMAND);
            return;
        }

        Position position = CommandParser.parsePosition(parts[0]);
        if (position == null) {
            out.println(CommonConstants.INVALID_CELL_REFERENCE_MESSAGE);
            return;
        }

        String valuePart = parts[1];

        MoveResult moveResult;
        if (CommonConstants.CLEAR.equalsIgnoreCase(valuePart)) {
            moveResult = board.clearCell(position);
        } else {
            Integer value = CommandParser.parseValue(valuePart);
            if (value == null) {
                out.println(CommonConstants.INVALID_VALUE_MESSAGE);
                return;
            }

            moveResult = board.placeValue(position, value);
        }

        out.println(moveResult.getMessage());
        out.println();
        out.println(CommonConstants.CURRENT_GRID);
        board.printBoard();
    }

}
