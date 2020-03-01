package com.queens;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void reportNone() {
        assertEquals("No solutions found (0 possibilities evaluated)", new Result(0, 0).report());
    }

    public void reportOne() {
        assertEquals("1 solution found (10 possibilities evaluated)", new Result(1, 10).report());
    }

    public void reportLots() {
        assertEquals("10 solution found (100 possibilities evaluated)", new Result(10, 100).report());
    }
}
