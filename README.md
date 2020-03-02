# Introduction
This is a command line program to find all possible placements of 
N queens on an NxN chessboard such that:

1. None of the queens can attack each other
2. No three queens are in a straight line at ANY angle

It implements three methods to find possible solutions:
1. Permutation: All possible permutations of queen placements are generated and evaluated
2. Elimination: Starting from the top of the board, possible queen placements are iteratively 
generated row by row, skipping configurations that would allow the queens to attack each other
3. Multithreaded Elimination: Similar to method 2 except that it uses multiple threads to generate the placements 

# Prerequisites
* Java version >= 9
* Gradle version >= 6.2

# Building
~~~ sh
$ git clone https://github.com/ediril/queen-placer.git
$ cd queen-placer
$ gradle jar
~~~

# Running tests
~~~ sh
$ gradle test
~~~
Note that development and testing is currently done on OSX 10.14 (Mojave)

# Usage
~~~ sh
queen-placer [-h] [-d] [-p | -mt] board_size

positional arguments:
  board_size

named arguments:
  -h, --help             show this help message and exit
  -d, --display
  -p, --permuting
  -mt, --multithreaded
~~~

# Example outputs
~~~ sh
$ java -jar build/libs/queen-placer.jar 4 -p -d
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
$ time java -jar build/libs/queen-placer.jar 14
Using 'elimination' method
27358552 solution nodes generated
365596 possibilities evaluated
4416 solutions found

real	0m25.639s
user	2m22.655s
sys	0m1.991s
~~~

~~~ sh
$ time java -jar build/libs/queen-placer.jar 14 -mt
Using 'multithreaded elimination' method
27358552 solution nodes generated
365596 possibilities evaluated
4416 solutions found

real	0m15.854s
user	0m29.597s
sys	0m11.104s
~~~