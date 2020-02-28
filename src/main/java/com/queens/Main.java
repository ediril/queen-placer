package com.queens;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("queen_placer").build();
        parser.addArgument("board_size").type(Integer.class);

        try {
            int N = parser.parseArgs(args).get("board_size");

            if (N == 0) {
                System.out.println("Invalid board_size, must be greater than zero");
                System.exit(1);
            }

            QueenPlacer placer = new QueenPlacer(N);
            int numSolutions = placer.findSolutions(QueenPlacer::displaySolution);
            System.out.println(QueenPlacer.report(numSolutions));

        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
}
