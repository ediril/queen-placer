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

        columns = IntStream.range(0, boardSize).boxed()
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Searches possible solutions by iteratively generating new solution nodes based on
     * previously placed queens, skipping possibilities that are not valid based on queen
     * attack patterns
     */
    public Result findSolutions(Consumer<List<Integer>> solution) {
        int numPossibilitiesEvaluated = 0;
        int numSolutions = 0;
        int numSolutionNodes = 0;
        Deque<List<Integer>> queue = new LinkedList<>();

        // Seed the queue
        createSolutionNodes(new ArrayList<>(boardSize), queue);

        while (queue.size() > 0) {
            List<Integer> solutionNode = queue.remove();
            numSolutionNodes++;

            if (solutionNode.size() == boardSize) {
                numPossibilitiesEvaluated++;

                if (!containsStraightLinePlacement(solutionNode)) {
                    numSolutions++;
                    solution.accept(solutionNode);
                }
            } else {
                createSolutionNodes(solutionNode, queue);
            }
        }

        System.out.println(String.format("%s solution nodes generated", numSolutionNodes));

        return new Result(numSolutions, numPossibilitiesEvaluated);
    }

    /**
     * Creates new solution nodes based on the current solution node
     */
    void createSolutionNodes(List<Integer> solutionNode, Deque<List<Integer>> queue) {
        int nextRow = solutionNode.size();
        Set<Integer> availableColumns = new HashSet<>(columns);

        // Eliminate available columns based on queens already added to the solution
        for (int row=0; row < solutionNode.size(); row++) {
            int column = solutionNode.get(row);
            // vertical
            availableColumns.remove(column);
            // right diagonal
            availableColumns.remove(column + (nextRow - row));
            // left diagonal
            availableColumns.remove(column - (nextRow - row));
        }

        // Create new solution nodes using the remaining blank columns
        for (int column : availableColumns) {
            List<Integer> newSolution = new ArrayList<>(solutionNode);
            newSolution.add(column);
            queue.add(newSolution);
        }
    }
}
