package com.queens;

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

            if (N == 0) {
                System.out.println("ERROR: Invalid input argument, N must be nonzero");
                System.exit(1);
            }

            QueenPlacer placer = new QueenPlacer(N);
            int numSolutions = placer.findSolutions(QueenPlacer::displaySolution);
            QueenPlacer.reportResults(numSolutions);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid input argument, N must be integer");
            System.exit(1);
        }
    }
}
