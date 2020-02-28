package com.queens;

import org.apache.commons.collections4.iterators.PermutationIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class QueenPlacer {

    private final int size;
    private final List<Integer> columns;

    public QueenPlacer(int size) {
        this.size = size;

        // Create the list of column numbers for the board
        columns = new ArrayList<>(size);
        for (int col=0; col < size; col++) {
            columns.add(col);
        }
    }

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
        Set<Integer> leftDiagonals = new HashSet<>(size);
        Set<Integer> rightDiagonals = new HashSet<>(size);
        while (permIterator.hasNext()) {
            List<Integer> permutation = permIterator.next();

            leftDiagonals.clear();
            rightDiagonals.clear();
            for (int row=0; row < size; row++) {
                int col = permutation.get(row);

                // "Left" slanted diagonals: col# - row#
                //  0  1  2  3
                // -1  0  1  2
                // -2 -1  0  1
                // -3 -2 -1  0
                leftDiagonals.add(col - row);

                // "Right" slanted diagonals: col# + row#
                //  0  1  2  3
                //  1  2  3  4
                //  2  3  4  5
                //  3  4  5  6
                rightDiagonals.add(col + row);
            }

            // If there is one of each column number in each set, then each queen is
            // on its own diagonal. Therefore they cannot attack each other
            if (leftDiagonals.size() == size && rightDiagonals.size() == size) {
                if (!containsStraightLine(permutation)) {
                    numSolutions++;
                    solution.accept(permutation);
                }
            }
        }

        return numSolutions;
    }

    public static boolean containsStraightLine(List<Integer> queens) {
        int N = queens.size();

        // Calculate slopes from each queen to other queens moving from top of board down
        Set<Float> slopes = new HashSet<>(N);
        for (int q1=0; q1 < N; q1++) {
            // Consider only queens below the current queen on the board
            for (int q2=q1+1; q2 < N; q2++) {
                int yDist = q2-q1;
                int xDist = queens.get(q2) - queens.get(q1);
                float slope = (float) yDist / xDist;
                slopes.add(slope);
            }

            // If there are non-unique slopes, then no-straight-line constraint is violated
            if (slopes.size() != (N-1) - q1) {
                return true;
            }
        }

        return false;
    }

    public static void displaySolution(List<Integer> queens) {
        System.out.println(queens);

        for (int queen : queens) {
            for (int col=0; col < queens.size(); col++) {
                if (queen == col)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static String report(int numSolutions) {
        return String.format("%s solution%s found",
                numSolutions == 0 ? "No" : numSolutions,
                numSolutions != 1 ? "s" : "");
    }
}
