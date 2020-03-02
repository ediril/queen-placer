package com.queens;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class PermutingQueenPlacerTest {

    @Test
    public void findSolutionsForSize4() {
        QueenPlacer placer = new PermutingQueenPlacer(4);

        List<List<Integer>> actual = new ArrayList<>();
        Result result = placer.findSolutions(actual::add);

        assertThat(actual, containsInAnyOrder(
                Arrays.asList(1, 3, 0, 2), Arrays.asList(2, 0, 3, 1)));
        assertEquals(4 * 3 * 2, result.numPossibilitiesEvaluated());
        assertEquals(2, result.numSolutions());
    }

    @Test
    public void findSolutionsForSize5() {
        QueenPlacer placer = new PermutingQueenPlacer(5);
        Result result = placer.findSolutions(__ -> {});

        assertEquals(5 * 4 * 3 * 2, result.numPossibilitiesEvaluated());
        assertEquals(0, result.numSolutions());
    }

    @Test
    public void findSolutionsForSize8() {
        QueenPlacer placer = new PermutingQueenPlacer(8);

        List<List<Integer>> actual = new ArrayList<>();
        Result result = placer.findSolutions(actual::add);

        assertEquals(8 * 7 * 6 * 5 * 4 * 3 * 2, result.numPossibilitiesEvaluated());
        assertEquals(8, result.numSolutions());
        assertThat(actual, containsInAnyOrder(
                Arrays.asList(4, 6, 0, 3, 1, 7, 5, 2),
                Arrays.asList(5, 3, 0, 4, 7, 1, 6, 2),
                Arrays.asList(5, 2, 0, 6, 4, 7, 1, 3),
                Arrays.asList(2, 4, 7, 3, 0, 6, 1, 5),
                Arrays.asList(2, 5, 7, 1, 3, 0, 6, 4),
                Arrays.asList(2, 6, 1, 7, 4, 0, 3, 5),
                Arrays.asList(3, 1, 7, 4, 6, 0, 2, 5),
                Arrays.asList(5, 1, 6, 0, 3, 7, 4, 2)));
    }
}
