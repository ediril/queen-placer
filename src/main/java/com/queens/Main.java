package com.queens;

import org.apache.commons.collections4.iterators.PermutationIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("ERROR: Missing input argument, provide an integer for N");
            System.exit(1);
        }

        if (args.length > 1) {
            System.out.println("ERROR: Too many input arguments");
            System.exit(1);
        }

        try {
            int N = Integer.parseInt(args[0]);

            // Create the list of column numbers for the board
            List<Integer> columns = new ArrayList<>(N);
            for (int col=0; col < N; col++) {
                columns.add(col);
            }

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

            Set<Integer> leftDiagonals = new HashSet<>(N);
            Set<Integer> rightDiagonals = new HashSet<>(N);
            while (permIterator.hasNext()) {
                List<Integer> permutation = permIterator.next();

                leftDiagonals.clear();
                rightDiagonals.clear();
                for (int row=0; row < N; row++) {
                    int col = permutation.get(row);

                    // "Left" slanted diagonals: col# - row#
                    //  0  1  2  3
                    // -1  0  1  2
                    // -2 -1  0  1
                    // -3 -2 -1  0
                    leftDiagonals.add(col + row);

                    // "Right" slanted diagonals: col# + row#
                    //  0  1  2  3
                    //  1  2  3  4
                    //  2  3  4  5
                    //  3  4  5  6
                    rightDiagonals.add(col - row);
                }

                // If there is one of each column number in each set, then each queen is
                // on its own diagonal. Therefore they cannot attack each other
                if (leftDiagonals.size() == N &&
                    rightDiagonals.size() == N) {
                    // TODO: Check no-straight-line rule

                    // TODO: Convert to ASCII picture
                    System.out.println(permutation);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input argument, N must be integer");
            System.exit(1);
        }
    }
}
