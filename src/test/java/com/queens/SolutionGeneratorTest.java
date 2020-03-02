package com.queens;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;

public class SolutionGeneratorTest {

    private BlockingDeque<List<Integer>> queue;
    private final Set<Integer> columns =
            IntStream.range(0, 4).boxed().collect(Collectors.toCollection(HashSet::new));

    @Before
    public void setup() {
        queue = new LinkedBlockingDeque<>();
    }

    @Test
    public void seedTheQueue() {
        SolutionGenerator placer = new SolutionGenerator(queue, columns, true);
        placer.run();

        List<List<Integer>> solutions = placer.solutions();
        int numSolutionNodes = placer.numSolutionNodes();

        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(1, 3, 0, 2));
        expected.add(Arrays.asList(2, 0, 3, 1));

        assertEquals(16, numSolutionNodes);
        assertEquals(2, solutions.size());
        assertThat(solutions, containsInAnyOrder(
                Arrays.asList(1, 3, 0, 2), Arrays.asList(2, 0, 3, 1)));
    }

    @Test
    public void preexistingQueueFirstRow() throws InterruptedException {
        // Add a single solution node as seed
        queue.putFirst(Collections.singletonList(1));

        SolutionGenerator placer = new SolutionGenerator(queue, columns);
        placer.run();

        List<List<Integer>> solutions = placer.solutions();
        int numSolutionNodes = placer.numSolutionNodes();

        assertEquals(4, numSolutionNodes);
        assertEquals(1, solutions.size());
        assertThat(solutions, containsInAnyOrder(
                Arrays.asList(1, 3, 0, 2)));
    }

    @Test
    public void preexistingQueueSecondRow() throws InterruptedException {
        // Add a single solution node as seed
        queue.putFirst(Arrays.asList(1, 3));

        SolutionGenerator placer = new SolutionGenerator(queue, columns);
        placer.run();

        List<List<Integer>> solutions = placer.solutions();
        int numSolutionNodes = placer.numSolutionNodes();

        assertEquals(3, numSolutionNodes);
        assertEquals(1, solutions.size());
        assertThat(solutions, containsInAnyOrder(
                Arrays.asList(1, 3, 0, 2)));
    }
}
