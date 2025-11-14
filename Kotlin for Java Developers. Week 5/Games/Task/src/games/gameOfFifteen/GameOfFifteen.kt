package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {

    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        // get an even initial permutation of numbers 1..15
        val numbers = initializer.initialPermutation
        var index = 0

        for (i in 1..board.width) {
            for (j in 1..board.width) {
                // check if this is the last cell
                val value = if (index < numbers.size) {
                    numbers[index++] // fill the first 15 cells with permuted tiles
                } else {
                    null // leave the last cell empty
                }
                board[board.getCell(i, j)] = value
            }
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        var number = 1

        for (i in 1..board.width) {
            for (j in 1..board.width) {
                val value = get(i, j)
                // the last cell should be empty
                if (i == board.width && j == board.width) {
                    if (value != null) {
                        return false // if the last cell is not empty, the game is not won
                    }
                } else {
                    if (value != number) {
                        return false // if the number is out of sequence, the game is not won
                    }
                    number++
                }
            }
        }

        return true // all tiles are in the correct order and last cell is empty
    }

    override fun processMove(direction: Direction) {
        with(board) {
            // find the first cell that currently contains no tile
            val emptyCell = board.getAllCells().first { cell -> board[cell] == null }

            // find the cell that contains the tile that should be moved into the empty cell
            val movingCell = emptyCell.getNeighbour(direction.reversed())!!

            // move the tile into the empty cell
            board[emptyCell] = board[movingCell]

            // mark the tile's original position as empty
            board[movingCell] = null
        }
    }

    override fun get(i: Int, j: Int): Int? = board[Cell(i, j)]
}
