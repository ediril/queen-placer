package com.queens;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class QueenPlacerTest {

    @Test
    public void reportResults() {
        assertEquals(QueenPlacer.report(0), "No solutions found");
        assertEquals(QueenPlacer.report(1), "1 solution found");
        assertEquals(QueenPlacer.report(2), "2 solutions found");
    }

    @Test
    public void solutionToAsciiBoard() {
        List<Integer> solution = Arrays.asList(4, 6, 0, 3, 1, 7, 5, 2);
        String expected =
                ". . . . Q . . . \n" +
                ". . . . . . Q . \n" +
                "Q . . . . . . . \n" +
                ". . . Q . . . . \n" +
                ". Q . . . . . . \n" +
                ". . . . . . . Q \n" +
                ". . . . . Q . . \n" +
                ". . Q . . . . . \n";
        String result = QueenPlacer.solutionToAsciiBoard(solution);

        assertEquals(result, expected);
    }

    @Test
    public void containsDiagonalAttack() {
        assertTrue( QueenPlacer.containsDiagonalAttack( Arrays.asList( 0, 1, 2, 3)));
        assertTrue( QueenPlacer.containsDiagonalAttack( Arrays.asList( 3, 2, 1, 0)));
    }

    @Test
    public void noDiagonalAttack() {
        assertFalse( QueenPlacer.containsDiagonalAttack( Arrays.asList( 1, 3, 0, 2)));
        assertFalse( QueenPlacer.containsDiagonalAttack( Arrays.asList( 5, 2, 0, 6, 4, 7, 1, 3)));
    }
}
