package com.queens;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

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

    @Test
    public void numberOfPossibleSolutions() {
        QueenPlacer placer = new QueenPlacer(6);
        int expected = 6 * 5 * 4 * 3 * 2;
        assertEquals(expected, placer.numberOfPossibleSolutions().intValue());
    }

    @Test
    public void findSolutionsForSize4() {
        QueenPlacer placer = new QueenPlacer(4);

        List<List<Integer>> actual = new ArrayList<>();
        placer.findSolutions(actual::add);

        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(1, 3, 0, 2));
        expected.add(Arrays.asList(2, 0, 3, 1));

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void findSolutionsForSize5() {
        QueenPlacer placer = new QueenPlacer(5);
        assertEquals(0, placer.findSolutions(__ -> {}));
    }

    @Test
    public void findSolutionsForSize8() {
        QueenPlacer placer = new QueenPlacer(8);

        List<List<Integer>> actual = new ArrayList<>();
        placer.findSolutions(actual::add);

        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(4, 6, 0, 3, 1, 7, 5, 2));
        expected.add(Arrays.asList(5, 3, 0, 4, 7, 1, 6, 2));
        expected.add(Arrays.asList(5, 2, 0, 6, 4, 7, 1, 3));
        expected.add(Arrays.asList(2, 4, 7, 3, 0, 6, 1, 5));
        expected.add(Arrays.asList(2, 5, 7, 1, 3, 0, 6, 4));
        expected.add(Arrays.asList(2, 6, 1, 7, 4, 0, 3, 5));
        expected.add(Arrays.asList(3, 1, 7, 4, 6, 0, 2, 5));
        expected.add(Arrays.asList(5, 1, 6, 0, 3, 7, 4, 2));

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }
}
