package org.sudoku.util;

import org.sudoku.constants.CommonConstants;
import org.sudoku.model.Position;

public final class CommandParser {

    private CommandParser() {
    }

    public static Position parsePosition(String input) {
        if (input == null || input.length() != 2) {
            return null;
        }

        char rowChar = Character.toUpperCase(input.charAt(0));
        char columnChar = input.charAt(1);

        if (rowChar < CommonConstants.FIRST_ROW_LABEL || rowChar > CommonConstants.LAST_ROW_LABEL) {
            return null;
        }

        if (columnChar < CommonConstants.FIRST_COLUMN_LABEL || columnChar > CommonConstants.LAST_COLUMN_LABEL) {
            return null;
        }

        int row = rowChar - CommonConstants.FIRST_ROW_LABEL;
        int column = columnChar - CommonConstants.FIRST_COLUMN_LABEL;

        return new Position(row, column);
    }

    public static Integer parseValue(String input) {
        try {
            int value = Integer.parseInt(input);
            return BoardUtils.isValueInRange(value) ? value : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
