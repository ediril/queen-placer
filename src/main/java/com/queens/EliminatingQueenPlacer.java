package com.queens;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EliminatingQueenPlacer extends QueenPlacer {

    private final int boardSize;
    private final Set<Integer> columns;

    public EliminatingQueenPlacer(int boardSize) {
        this.boardSize = boardSize;
        this.columns = IntStream.range(0, boardSize).boxed().collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Searches possible solutions by iteratively generating new solution nodes based on
     * previously placed queens, removing configurations where queens can attack each other
     */
    public Result findSolutions(Consumer<List<Integer>> solution) {
        int numPossibilitiesEvaluated = 0;
        int numSolutions = 0;
        int numSolutionNodes = 0;

        // Seed the queue
        List<List<Integer>> newSolutionNodes = createSolutionNodes(new ArrayList<>(boardSize), columns);
        Deque<List<Integer>> queue = new LinkedList<>(newSolutionNodes);

        while (!queue.isEmpty()) {
            List<Integer> solutionNode = queue.remove();
            numSolutionNodes++;

            if (solutionNode.size() == boardSize) {
                numPossibilitiesEvaluated++;

                if (!containsStraightLinePlacement(solutionNode)) {
                    numSolutions++;
                    solution.accept(solutionNode);
                }
            } else {
                newSolutionNodes = createSolutionNodes(solutionNode, columns);
                queue.addAll(newSolutionNodes);
            }
        }

        System.out.println(String.format("%s solution nodes generated", numSolutionNodes));

        return new Result(numSolutions, numPossibilitiesEvaluated);
    }
}
