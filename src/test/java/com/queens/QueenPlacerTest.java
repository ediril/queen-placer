package com.queens;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueenPlacerTest {

    @Test
    public void reportResults() {
        assertEquals(QueenPlacer.report(0), "No solutions found");
        assertEquals(QueenPlacer.report(1), "1 solution found");
        assertEquals(QueenPlacer.report(2), "2 solutions found");
    }

}
