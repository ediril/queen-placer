package com.queens;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("queen_placer").build();
        parser.addArgument("board_size").type(Integer.class).required(true);
        parser.addArgument("-p", "--permuting").action(Arguments.storeConst()).setConst(true).setDefault(false);
        parser.addArgument("-d", "--display").action(Arguments.storeConst()).setConst(true).setDefault(false);

        try {
            Namespace ns = parser.parseArgs(args);
            int N = ns.get("board_size");

            if (N == 0) {
                System.out.println("Invalid board_size, must be greater than zero");
                System.exit(1);
            }

            System.out.println(String.format("Using %s method",
                    ns.get("permuting") ? "'permutation'" : "'elimination'"));

            QueenPlacer placer = ns.get("permuting") ?
                    new PermutingQueenPlacer(N) : new EliminatingQueenPlacer(N);

            Result result = placer.findSolutions(solution -> {
                if (ns.get("display")) {
                    System.out.println(QueenPlacer.solutionToAscii(solution));
                }
            });

            System.out.println(result.report());

        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
}
