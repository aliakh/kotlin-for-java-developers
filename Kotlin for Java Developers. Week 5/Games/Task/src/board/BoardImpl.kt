package board

import board.Direction.*

open class SquareBoardImpl(final override val width: Int) : SquareBoard {

    private var board: Array<Array<Cell>> =
        Array(width) { i ->
            Array(width) { j ->
                Cell(i + 1, j + 1)
            }
        }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        when {
            i <= 0 || j <= 0 || i > width || j > width -> null
            else -> board[i - 1][j - 1]
        }

    override fun getCell(i: Int, j: Int): Cell =
        getCellOrNull(i, j) ?: throw IllegalArgumentException("Coordinates ($i, $j) are outside the board (width = $width)")

    override fun getAllCells(): Collection<Cell> =
        board.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val row = ArrayList<Cell>()
        for (j in jRange) {
            if (j in 1..width) {
                row.add(board[i - 1][j - 1])
            }
        }
        return row
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = ArrayList<Cell>()
        for (i in iRange) {
            if (i in 1..width) {
                column.add(board[i - 1][j - 1])
            }
        }
        return column
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            UP -> getCellOrNull(i - 1, j)
            LEFT -> getCellOrNull(i, j - 1)
            DOWN -> getCellOrNull(i + 1, j)
            RIGHT -> getCellOrNull(i, j + 1)
        }
}

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val cellToValue = mutableMapOf<Cell, T?>()

    init {
        getAllCells().forEach { cell ->
            cellToValue[cell] = null
        }
    }

    override fun get(cell: Cell): T? {
        return cellToValue[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cellToValue[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellToValue
            .filter { (_, value) -> predicate(value) }
            .map { (cell, _) -> cell }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return filter(predicate).firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return find(predicate) != null
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return filter(predicate).size == cellToValue.size
    }
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)
