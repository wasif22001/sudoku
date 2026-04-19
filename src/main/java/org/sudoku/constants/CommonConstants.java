package org.sudoku.constants;

public class CommonConstants {

    private CommonConstants() {
    }

    public static final int GRID_SIZE = 9;
    public static final int SUBGRID_SIZE = 3;
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 9;
    public static final int EMPTY_CELL = 0;

    public static final char FIRST_ROW_LABEL = 'A';
    public static final char LAST_ROW_LABEL = 'I';
    public static final char FIRST_COLUMN_LABEL = '1';
    public static final char LAST_COLUMN_LABEL = '9';

    public static final String UNDERSCORE = "_";

    public static final String HINT = "hint";
    public static final String CHECK = "check";
    public static final String QUIT = "quit";
    public static final String CLEAR = "clear";

    public static final String WELCOME_TO_SUDOKU = "Welcome to Sudoku!";
    public static final String IS_YOUR_PUZZLE = "Here is your puzzle:";
    public static final String CURRENT_GRID = "Current grid:";
    public static final String INPUT_PROMPT =
            "Enter command (e.g., A3 4, C5 clear, hint, check, quit): ";

    public static final String INVALID_COMMAND = "Invalid command.";
    public static final String INVALID_CELL_REFERENCE_MESSAGE = "Invalid cell reference. Use A1 to I9.";
    public static final String INVALID_VALUE_MESSAGE = "Invalid value. Enter a number between 1 and 9 or 'clear'.";
    public static final String VALUE_OUT_OF_RANGE_MESSAGE = "Invalid value. Number must be between 1 and 9.";

    public static final String MOVE_ACCEPTED = "Move accepted.";
    public static final String CELL_CLEARED = "Cell cleared.";
    public static final String NO_RULE_VIOLATIONS_DETECTED = "No rule violations detected.";
    public static final String NO_HINT_AVAILABLE_MESSAGE = "No hint available. The grid is already full.";
    public static final String WIN_MESSAGE = "You have successfully completed the Sudoku puzzle!";
    public static final String GOODBYE = "Goodbye!";
    public static final String REPLAY_PROMPT = "Press Enter to play again or type 'quit' to exit: ";
    public static final String THANKS_FOR_PLAYING_SUDOKU = "Thanks for playing Sudoku!";

    public static final int[][] DEFAULT_PUZZLE = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    public static final int[][] DEFAULT_SOLUTION = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

}
