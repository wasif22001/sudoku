package org.sudoku.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuGameServiceTest {

    @Test
    void shouldShowInvalidCommandMessageForBadInput() {
        String input = "wrong-command\nquit\n";
        Scanner scanner = new Scanner(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        SudokuGameService game = new SudokuGameService(scanner, out, new Random(1));
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid command."));
    }

    @Test
    void shouldAcceptValidMove() {
        String input = "A3 4\nquit\n";
        Scanner scanner = new Scanner(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        SudokuGameService game = new SudokuGameService(scanner, out, new Random(1));
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Move accepted."));
    }

    @Test
    void shouldRejectMoveOnPreFilledCell() {
        String input = "A1 6\nquit\n";
        Scanner scanner = new Scanner(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        SudokuGameService game = new SudokuGameService(scanner, out, new Random(1));
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid move. A1 is pre-filled."));
    }

    @Test
    void shouldHandleCheckCommand() {
        String input = "check\nquit\n";
        Scanner scanner = new Scanner(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        SudokuGameService game = new SudokuGameService(scanner, out, new Random(1));
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("No rule violations detected."));
    }

    @Test
    void shouldHandleHintCommand() {
        String input = "hint\nquit\n";
        Scanner scanner = new Scanner(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        SudokuGameService game = new SudokuGameService(scanner, out, new Random(1));
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Hint: Cell"));
    }

}
