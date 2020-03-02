package com.queens;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("java -jar queen-placer").build();
        parser.addArgument("board_size").type(Integer.class).required(true);
        parser.addArgument("-d", "--display").action(Arguments.storeConst()).setConst(true).setDefault(false);
        MutuallyExclusiveGroup group = parser.addMutuallyExclusiveGroup();
        group.addArgument("-p", "--permuting").action(Arguments.storeConst()).setConst(true).setDefault(false);
        group.addArgument("-mt", "--multithreaded").action(Arguments.storeConst()).setConst(true).setDefault(false);

        try {
            Namespace ns = parser.parseArgs(args);
            int N = ns.get("board_size");

            if (N == 0) {
                System.out.println("Invalid board_size, must be greater than zero");
                System.exit(1);
            }

            QueenPlacer placer;
            if (ns.get("permuting")) {
                placer = new PermutingQueenPlacer(N);
                System.out.println("Using 'permutation' method");
            } else if (ns.get("multithreaded")) {
                placer = new MultithreadedEliminatingQueenPlacer(N);
                System.out.println("Using 'multithreaded elimination' method");
            } else {
                placer = new EliminatingQueenPlacer(N);
                System.out.println("Using 'elimination' method");
            }

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
