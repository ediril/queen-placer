package com.queens;

import org.apache.commons.collections4.iterators.PermutationIterator;

import java.util.ArrayList;
import java.util.List;

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

        }
        catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input argument, N must be integer");
            System.exit(1);
        }
    }
}
