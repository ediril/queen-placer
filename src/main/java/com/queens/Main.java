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

            // Generate a list of column id's
            List<Integer> columns = new ArrayList<>(N);
            for (int c=0; c < N; c++) {
                columns.add(c);
            }

            // Steinhaus-Johnson-Trotter algorithm (aka Plain Changes algorithm)
            // to generate all permutations of one queen per row & col
            PermutationIterator<Integer> permIterator = new PermutationIterator<>(columns);

            // Since we are using a permutation algorithm on column id's, there will be
            // exactly one queen per row and per column. So, we just need to check
            // attacks diagonally and the no-straight-line constraint
            //
            // Left to right diagonals: col# - row#
            //  0  1  2  3
            // -1  0  1  2
            // -2 -1  0  1
            // -3 -2 -1  0
            //
            // Right to left diagonals: col# + row#
            //  0  1  2  3
            //  1  2  3  4
            //  2  3  4  5
            //  3  4  5  6
            Set<Integer> rightDiagonals = new HashSet<>(N);
            Set<Integer> leftDiagonals = new HashSet<>(N);

            while (permIterator.hasNext()) {
                List<Integer> permutation = permIterator.next();

                leftDiagonals.clear();
                rightDiagonals.clear();
                for (int row=0; row < N; row++) {
                    int col = permutation.get(row);
                    leftDiagonals.add(col + row);
                    rightDiagonals.add(col - row);
                }

                // If there is one of each column id's in each set, it means
                // each queen is on its own diagonal so none can attack another
                if (leftDiagonals.size() == N &&
                    rightDiagonals.size() == N) {
                    System.out.println(permutation);
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input argument, N must be integer");
            System.exit(1);
        }
    }
}
