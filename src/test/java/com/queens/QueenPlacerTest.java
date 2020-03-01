package com.queens;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class QueenPlacerTest {

    @Test
    public void reportResults() {
        assertEquals("No solutions found", QueenPlacer.report(0));
        assertEquals("1 solution found", QueenPlacer.report(1));
        assertEquals("2 solutions found", QueenPlacer.report(2));
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
        String actual = QueenPlacer.solutionToAsciiBoard(solution);

        assertEquals(expected, actual);
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

    @Test
    public void containsStraightLinePlacement() {
        assertTrue( QueenPlacer.containsStraightLinePlacement( Arrays.asList( 0, 2, 4, 1, 0)));
        assertTrue( QueenPlacer.containsStraightLinePlacement( Arrays.asList( 4, 2, 0, 1, 0)));
        assertTrue( QueenPlacer.containsStraightLinePlacement( Arrays.asList( 0, 3, 1, 4, 2)));
        assertTrue( QueenPlacer.containsStraightLinePlacement( Arrays.asList( 2, 3, 1, 4, 0)));
    }

    @Test
    public void noStraightLinePlacement() {
        assertFalse( QueenPlacer.containsStraightLinePlacement( Arrays.asList(2, 0, 3, 1)));
        assertFalse( QueenPlacer.containsStraightLinePlacement( Arrays.asList(0, 3, 1, 4, 3)));
        assertFalse( QueenPlacer.containsStraightLinePlacement( Arrays.asList(5, 3, 0, 4, 7, 1, 6, 2)));
    }
}
