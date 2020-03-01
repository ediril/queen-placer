package com.queens;

import org.apache.commons.collections4.iterators.PermutationIterator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PermutingQueenPlacer extends QueenPlacer {

    private final List<Integer> columns;
    private final int boardSize;

    public PermutingQueenPlacer(int boardSize) {
        this.boardSize = boardSize;

        // Create the list of column numbers for the board
        columns = new ArrayList<>(boardSize);
        for (int col=0; col < boardSize; col++) {
            columns.add(col);
        }
    }

    /**
     * Iteratively generates permutations of possible queen placements and
     * finds the ones that obey the following two constraints:
     * 1) None of the queens can attack each other
     * 2) No three queens are in a straight line at ANY angle
     *
     * @param solution  lambda function to call when a solution is found
     * @return          number of found solutions
     */
    public int findSolutions(Consumer<List<Integer>> solution) {
        // Iteratively generate permutations of column numbers
        // Uses Steinhaus-Johnson-Trotter algorithm, aka "Plain Changes")
        PermutationIterator<Integer> permIterator = new PermutationIterator<>(columns);

        // Each permutation is a list of size N to represent queen locations on the
        // board. Indices indicate the row number of the queen whereas values
        // indicate the column number. This representation automatically enforces that
        // there is only one queen per row and per column (partially fulfilling the requirement
        // that queens can't attack each other). Row numbers and column numbers start at 0
        // and (0,0) is the top left corner of the board.
        //
        // Ex: the list [2, 3, 1, 0, 4] translates to the following board:
        //   0 1 2 3 4
        // 0 . . Q . .
        // 1 . . . Q .
        // 2 . Q . . .
        // 3 Q . . . .
        // 4 . . . . Q

        int numSolutions = 0;
        while (permIterator.hasNext()) {
            List<Integer> permutation = permIterator.next();

            if (!containsDiagonalAttack(permutation) && !containsStraightLinePlacement(permutation)) {
                numSolutions++;
                solution.accept(permutation);
            }
        }

        return numSolutions;
    }

    /**
     * Calculates number of possible solutions for the given board size
     */
    public BigInteger numberOfPossibleSolutions() {
        BigInteger factorial = new BigInteger("1");
        for (int i = 1; i <= boardSize; i++) {
            factorial = factorial.multiply(new BigInteger(i + ""));
        }

        return factorial;
    }
}
