# Introduction
This is a command line program to find all possible placements of 
N queens on an NxN chessboard such that:

1. None of the queens can attack each other
2. No three queens are in a straight line at ANY angle

# Prerequisites
* Java version >= 8
* Gradle version >= 6.2

# Building
        git clone https://github.com/ediril/queen_placer.git
        cd queen_placer
        gradle jar

# Running tests
        gradle test

# Usage
        java -jar build/libs/queen_placer-1.0.0.jar <board_size>

# Example output
        java -jar build/libs/queen_placer-1.0.0.jar 4

        Searching through 24 possible solutions

        . . Q .
        Q . . .
        . . . Q
        . Q . .
        
        . Q . .
        . . . Q
        Q . . .
        . . Q .
        
        2 solutions found
