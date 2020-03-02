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
    public void emptyQueue() {
        SolutionGenerator placer = new SolutionGenerator(queue, columns);
        placer.run();

        List<List<Integer>> solutions = placer.solutions();
        int numSolutionNodes = placer.numSolutionNodes();

        assertEquals(0, numSolutionNodes);
        assertEquals(0, solutions.size());
    }

    @Test
    public void seededQueue() throws InterruptedException {
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
}
