package com.queens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

public class SolutionGenerator implements Runnable {
    private int numSolutionNodes = 0;
    private final List<List<Integer>> solutions;

    private final BlockingDeque<List<Integer>> queue;
    private final boolean seedTheQueue;
    private final Set<Integer> columns;
    private final int boardSize;

    public SolutionGenerator(BlockingDeque<List<Integer>> queue, Set<Integer> columns) {
        this(queue, columns, false);
    }

    public SolutionGenerator(BlockingDeque<List<Integer>> queue, Set<Integer> columns, boolean seedTheQueue) {
        this.queue = queue;
        this.columns = columns;
        this.boardSize = columns.size();
        this.seedTheQueue = seedTheQueue;
        this.solutions = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            if (seedTheQueue) {
                createSolutionNodes(new ArrayList<>(boardSize), queue);
            } else {
                while (queue.isEmpty()) {
                    Thread.sleep(500);
                }
            }

            while (!queue.isEmpty()) {
                List<Integer> solutionNode = queue.pollFirst(2, TimeUnit.SECONDS);
                if (solutionNode == null) {
                    break;
                }

                numSolutionNodes++;

                if (solutionNode.size() == boardSize) {
                    solutions.add(solutionNode);
                } else {
                    createSolutionNodes(solutionNode, queue);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }

    /**
     * Creates new solution nodes based on the current solution node
     */
    void createSolutionNodes(List<Integer> solutionNode, BlockingDeque<List<Integer>> queue)
            throws InterruptedException {
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
            queue.putFirst(newSolution);
        }
    }

    public int numSolutionNodes() {
        return numSolutionNodes;
    }

    public List<List<Integer>> solutions() {
        return solutions;
    }
}
