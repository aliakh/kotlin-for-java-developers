package games.gameOfFifteen

import java.util.Random

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        generateSequence {
            // create a random permutation of numbers 1..15
            (1..15).shuffled()
        }.first { permutation ->
            // accept the first permutation that has even parity
            isEven(permutation)
        }
    }
}
