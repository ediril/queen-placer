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
    private final Set<Integer> columns;
    private final int boardSize;

    public SolutionGenerator(BlockingDeque<List<Integer>> queue, Set<Integer> columns) {
        this.queue = queue;
        this.columns = columns;
        this.boardSize = columns.size();
        this.solutions = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (!queue.isEmpty()) {
                List<Integer> solutionNode = queue.pollFirst(2, TimeUnit.SECONDS);
                if (solutionNode == null) {
                    break;
                }

                numSolutionNodes++;

                if (solutionNode.size() == boardSize) {
                    solutions.add(solutionNode);
                } else {
                    List<List<Integer>> newSolutionNodes = QueenPlacer.createSolutionNodes(solutionNode, columns);
                    for (List<Integer> node : newSolutionNodes) {
                        queue.putFirst(node);
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }

    public int numSolutionNodes() {
        return numSolutionNodes;
    }

    public List<List<Integer>> solutions() {
        return solutions;
    }
}
