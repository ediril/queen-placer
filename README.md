# Introduction
This is a command line program to find all possible placements of 
N queens on an NxN chessboard such that:

1. None of the queens can attack each other
2. No three queens are in a straight line at ANY angle

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
 java -jar build/libs/queen_placer-1.0.0.jar <board_size>
~~~

# Example outputs
~~~ sh
 java -jar build/libs/queen_placer-1.0.0.jar 4 -p -d
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
 java -jar build/libs/queen_placer-1.0.0.jar 4 -d
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