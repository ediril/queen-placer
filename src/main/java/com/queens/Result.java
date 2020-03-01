package com.queens;

public class Result {
    private final int numSolutions;
    private final int numPossibilitiesEvaluated;

    public Result(int numSolutions, int numPossibilitiesEvaluated) {
        this.numSolutions = numSolutions;
        this.numPossibilitiesEvaluated = numPossibilitiesEvaluated;
    }

    public String report() {
        return String.format("%s solution%s found (%s possibilities evaluated)",
                numSolutions == 0 ? "No" : numSolutions,
                numSolutions != 1 ? "s" : "",
                numPossibilitiesEvaluated);
    }
}
