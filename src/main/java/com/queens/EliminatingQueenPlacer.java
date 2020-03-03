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
     * previously placed queens, where a solution node is an arrangement of queens on a chessboard
     * starting from the top of the board moving downward, represented as a list of column indices
     * with one queen per row.
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

            // If all queens are added to the board, add it to the list of potential solutions
            if (solutionNode.size() == boardSize) {
                numPossibilitiesEvaluated++;

                if (!containsStraightLinePlacement(solutionNode)) {
                    numSolutions++;
                    solution.accept(solutionNode);
                }
            } else {
                // There are more queens to be placed on the board, generate more solution nodes
                newSolutionNodes = createSolutionNodes(solutionNode, columns);
                queue.addAll(newSolutionNodes);
            }
        }

        System.out.println(String.format("%s solution nodes generated", numSolutionNodes));

        return new Result(numSolutions, numPossibilitiesEvaluated);
    }
}
