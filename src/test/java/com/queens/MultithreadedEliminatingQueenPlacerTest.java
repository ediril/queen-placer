package com.queens;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.BlockingDeque;

import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.when;

public class MultithreadedEliminatingQueenPlacerTest {

    @Test
    public void findSolutions() {
        QueenPlacer mockPlacer = new MultithreadedEliminatingQueenPlacer(4) {
            @Override
            protected List<SolutionNodeGenerator> createGenerators(
                    BlockingDeque<List<Integer>> queue, Set<Integer> columns) {
                List<SolutionNodeGenerator> generators = new ArrayList<>();

                SolutionNodeGenerator generator1 = Mockito.mock(SolutionNodeGenerator.class);
                generators.add(generator1);
                when(generator1.potentialSolutions()).thenReturn(
                        Arrays.asList(Arrays.asList(1, 3, 0, 2), Arrays.asList(0, 1, 2, 3)));

                SolutionNodeGenerator generator2 = Mockito.mock(SolutionNodeGenerator.class);
                generators.add(generator2);
                when(generator2.potentialSolutions()).thenReturn(
                        Arrays.asList(Arrays.asList(2, 0, 3, 1), Arrays.asList(3, 2, 1, 0)));

                return generators;
            }

            @Override
            protected List<Thread> createThreads(List<SolutionNodeGenerator> generators) {
                return Collections.emptyList();
            }
        };

        List<List<Integer>> actual = new ArrayList<>();
        Result result = mockPlacer.findSolutions(actual::add);

        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(1, 3, 0, 2));
        expected.add(Arrays.asList(2, 0, 3, 1));

        assertThat(actual, containsInAnyOrder(expected.toArray()));
        assertEquals(4, result.numPossibilitiesEvaluated());
        assertEquals(2, result.numSolutions());
    }
}
