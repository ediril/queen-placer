package com.queens;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void reportNone() {
        assertEquals("0 possibilities evaluated\nNo solutions found", new Result(0, 0).report());
    }

    @Test
    public void reportOne() {
        assertEquals("10 possibilities evaluated\n1 solution found", new Result(1, 10).report());
    }

    @Test
    public void reportLots() {
        assertEquals("100 possibilities evaluated\n10 solutions found", new Result(10, 100).report());
    }
}
