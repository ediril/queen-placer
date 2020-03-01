# Introduction
This is a command line program to find all possible placements of 
N queens on an NxN chessboard such that:

1. None of the queens can attack each other
2. No three queens are in a straight line at ANY angle

It implements two methods to find possible solutions:
1. Permutation: All possible permutations of queen placements are generated and evaluated
2. Elimination: Starting from the top of the board, possible queen placements are generated 
iteratively generated row by row, skipping configurations that are not valid    

# Prerequisites
* Java version >= 8
* Gradle version >= 6.2

# Building
~~~ sh
 git clone https://github.com/ediril/queen_placer.git
 cd queen_placer
 gradle jar
~~~

# Running tests
~~~ sh
 gradle test
~~~

# Usage
~~~ sh
 java -jar build/libs/queen_placer [-h] [-p] [-d] board_size

 positional arguments:
   board_size

 named arguments:
   -h, --help             show this help message and exit
   -p, --permuting
   -d, --display
~~~

# Example outputs
~~~ sh
 java -jar build/libs/queen_placer.jar 4 -p -d
 Using 'permutation' method
 . . Q .
 Q . . .
 . . . Q
 . Q . .

 . Q . .
 . . . Q
 Q . . .
 . . Q .

 24 possibilities evaluated
 2 solutions found
~~~

~~~ sh
 java -jar build/libs/queen_placer.jar 4 -d
 Using 'elimination' method
 . . Q .
 Q . . .
 . . . Q
 . Q . .

 . Q . .
 . . . Q
 Q . . .
 . . Q .

 24 possibilities evaluated
 2 solutions found
~~~