package com.queens;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultithreadedEliminatingQueenPlacer extends QueenPlacer {

    private final int boardSize;
    private final int threadCount;
    private final Set<Integer> columns;

    public MultithreadedEliminatingQueenPlacer(int boardSize) {
        this(boardSize, 8);
    }

    public MultithreadedEliminatingQueenPlacer(int boardSize, int threadCount) {
        this.boardSize = boardSize;
        this.threadCount = threadCount;
        this.columns = IntStream.range(0, boardSize).boxed().collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Implements the logic of {@link EliminatingQueenPlacer} using multiple threads to
     * generate solution nodes.
     */
    public Result findSolutions(Consumer<List<Integer>> solution) {
        // Seed the queue
        List<List<Integer>> newSolutionNodes = createSolutionNodes(new ArrayList<>(boardSize), columns);
        BlockingDeque<List<Integer>> queue = new LinkedBlockingDeque<>(newSolutionNodes);

        // Create threads for generating solution nodes
        List<SolutionNodeGenerator> generators = createGenerators(queue, columns);
        List<Thread> threads = createThreads(generators);
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("threads interrupted");
        }

        // Collect the results from threads into the final result
        int numSolutionNodes = 0;
        int numPossibilitiesEvaluated = 0;
        int numSolutions = 0;
        for (SolutionNodeGenerator generator : generators) {
            numSolutionNodes += generator.numSolutionNodes();

            for (List<Integer> potentialSolution : generator.potentialSolutions()) {
                numPossibilitiesEvaluated++;

                if (!containsStraightLinePlacement(potentialSolution)) {
                    numSolutions++;
                    solution.accept(potentialSolution);
                }
            }
        }

        System.out.println(String.format("%s solution nodes generated", numSolutionNodes));

        return new Result(numSolutions, numPossibilitiesEvaluated);
    }

    protected List<SolutionNodeGenerator> createGenerators(BlockingDeque<List<Integer>> queue, Set<Integer> columns) {
        List<SolutionNodeGenerator> generators = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            generators.add(new SolutionNodeGenerator(queue, columns));
        }
        return generators;
    }

    protected List<Thread> createThreads(List<SolutionNodeGenerator> generators) {
        List<Thread> threads = new ArrayList<>(threadCount);
        for (SolutionNodeGenerator generator : generators) {
            Thread t = new Thread(generator);
            t.start();
            threads.add(t);
        }
        return threads;
    }
}
