package com.queens;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class EliminatingQueenPlacerTest {

    @Test
    public void solutionNodesEmptyQueue() {
        EliminatingQueenPlacer placer = new EliminatingQueenPlacer(3);
        Deque<List<Integer>> queue = new LinkedList<>();

        placer.createSolutionNodes(new ArrayList<>(), queue);

        assertEquals(3, queue.size());
        assertTrue(queue.containsAll(Arrays.asList(
                Collections.singletonList(0), Collections.singletonList(1), Collections.singletonList(2)
        )));
    }

    @Test
    public void solutionNodesRow1() {
        EliminatingQueenPlacer placer = new EliminatingQueenPlacer(3);
        Deque<List<Integer>> queue = new LinkedList<>();

        placer.createSolutionNodes(Collections.singletonList(0), queue);

        assertEquals(1, queue.size());
        assertThat(queue, contains(Arrays.asList(0, 2)));
    }

    @Test
    public void solutionNodesRow2() {
        EliminatingQueenPlacer placer = new EliminatingQueenPlacer(4);
        Deque<List<Integer>> queue = new LinkedList<>();

        placer.createSolutionNodes(Arrays.asList(0, 3), queue);

        assertEquals(1, queue.size());
        assertThat(queue, contains(Arrays.asList(0, 3, 1)));
    }

    @Test
    public void findSolutionsForSize4() {
        QueenPlacer placer = new EliminatingQueenPlacer(4);

        List<List<Integer>> actual = new ArrayList<>();
        Result result = placer.findSolutions(actual::add);

        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(1, 3, 0, 2));
        expected.add(Arrays.asList(2, 0, 3, 1));

        assertThat(actual, containsInAnyOrder(expected.toArray()));
        assertEquals(2, result.numPossibilitiesEvaluated());
        assertEquals(2, result.numSolutions());
    }

    @Test
    public void findSolutionsForSize5() {
        QueenPlacer placer = new EliminatingQueenPlacer(5);
        Result result = placer.findSolutions(__ -> {});

        assertEquals(10, result.numPossibilitiesEvaluated());
        assertEquals(0, result.numSolutions());
    }

    @Test
    public void findSolutionsForSize8() {
        QueenPlacer placer = new EliminatingQueenPlacer(8);

        List<List<Integer>> actual = new ArrayList<>();
        Result result = placer.findSolutions(actual::add);

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
        assertEquals(92, result.numPossibilitiesEvaluated());
        assertEquals(8, result.numSolutions());
    }
}
