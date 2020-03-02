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
     * Uses multiple threads to generate solution nodes.Nodes are generated iteratively based
     * on previously placed queens, skipping possibilities that are not valid based on queen
     * attack patterns.
     */
    public Result findSolutions(Consumer<List<Integer>> solution) {
        // Seed the queue
        List<List<Integer>> newSolutionNodes = createSolutionNodes(new ArrayList<>(boardSize), columns);
        BlockingDeque<List<Integer>> queue = new LinkedBlockingDeque<>(newSolutionNodes);

        // Create threads for generating solution nodes
        List<SolutionGenerator> generators = createGenerators(queue, columns);
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
        for (SolutionGenerator generator : generators) {
            numSolutionNodes += generator.numSolutionNodes();

            for (List<Integer> solutionNode : generator.solutions()) {
                numPossibilitiesEvaluated++;

                if (!containsStraightLinePlacement(solutionNode)) {
                    numSolutions++;
                    solution.accept(solutionNode);
                }
            }
        }

        System.out.println(String.format("%s solution nodes generated", numSolutionNodes));

        return new Result(numSolutions, numPossibilitiesEvaluated);
    }

    protected List<SolutionGenerator> createGenerators(BlockingDeque<List<Integer>> queue, Set<Integer> columns) {
        List<SolutionGenerator> generators = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            generators.add(new SolutionGenerator(queue, columns));
        }
        return generators;
    }

    protected List<Thread> createThreads(List<SolutionGenerator> generators) {
        List<Thread> threads = new ArrayList<>(threadCount);
        for (SolutionGenerator generator : generators) {
            Thread t = new Thread(generator);
            t.start();
            threads.add(t);
        }
        return threads;
    }
}
