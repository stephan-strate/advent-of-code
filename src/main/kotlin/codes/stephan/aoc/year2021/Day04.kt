package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day

object Day04 : Day(2021, 4) {

    override fun first(data: String): Any {
        val input = data.parse()
        return input.scores()
            .first()
    }

    override fun second(data: String): Any {
        val input = data.parse()
        return input.scores()
            .last()
    }

    private fun Bingo.scores(): List<Int> {
        val winners = mutableListOf<Int>()
        val won = mutableSetOf<Board>()

        numbers.forEach { number ->
            (boards - won).forEach { board ->
                if (board.neighbours.contains(number)) {
                    val adj = board.neighbours[number]!!
                    board.neighbours[number] = adj.copy(marked = true)

                    val row = adj.row.all { board.neighbours[it]!!.marked }
                    val column = adj.column.all { board.neighbours[it]!!.marked }
                    if (row || column) {
                        val sumOfFalse = board.neighbours.filter { (_, value) -> !value.marked }
                            .map { (key, _) -> key }
                            .sum()
                        winners.add(sumOfFalse * number)
                        won.add(board)
                    }
                }
            }
        }

        return winners
    }

    private fun String.parse(): Bingo {
        val lines = lines().iterator()
        val numbers = lines.next()
            .split(",")
            .map { it.toInt() }

        val boards = mutableListOf<Board>()

        while (lines.hasNext()) {
            lines.next() // skip empty line

            // read values as matrix
            val matrix = Array(5) { IntArray(5) }
            repeat(5) { rowIndex ->
                lines.next()
                    .split("  ", " ")
                    .filter { it != "" }
                    .forEachIndexed { columnIndex, value ->
                        matrix[rowIndex][columnIndex] = value.toInt()
                    }
            }

            // create sets of neighbours
            val board = mutableMapOf<Int, Neighbours>()
            matrix.forEach { row ->
                row.forEachIndexed { columnIndex, value ->
                    board[value] = Neighbours(
                        marked = false,
                        row = row.toSet() - value,
                        column = matrix.map { it[columnIndex] }.toSet() - value,
                    )
                }
            }
            boards.add(Board(board))
        }

        return Bingo(numbers, boards)
    }

    data class Bingo(
        val numbers: List<Int>,
        val boards: MutableList<Board>,
    )

    data class Board(
        val neighbours: MutableMap<Int, Neighbours>,
    )

    data class Neighbours(
        val marked: Boolean = false,
        val row: Set<Int>,
        val column: Set<Int>,
    )

    override val test = Test(
        data = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n" +
                "\n" +
                "22 13 17 11  0\n" +
                " 8  2 23  4 24\n" +
                "21  9 14 16  7\n" +
                " 6 10  3 18  5\n" +
                " 1 12 20 15 19\n" +
                "\n" +
                " 3 15  0  2 22\n" +
                " 9 18 13 17  5\n" +
                "19  8  7 25 23\n" +
                "20 11 10 24  4\n" +
                "14 21 16 12  6\n" +
                "\n" +
                "14 21 17 24  4\n" +
                "10 16 15  9 19\n" +
                "18  8 23 26 20\n" +
                "22 11 13  6  5\n" +
                " 2  0 12  3  7",
        firstSolution = 4512,
        secondSolution = 1924,
    )
}

fun main() = Day04.execute()
