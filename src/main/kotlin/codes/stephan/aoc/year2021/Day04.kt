package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day

object Day04 : Day(2021, 4) {

    override fun first(data: String): Any {
        val input = data.parse()

        input.numbers.forEach { number ->
            input.boards.forEach { board ->
                if (board.contains(number)) {
                    val adj = board[number]!!
                    board[number] = adj.copy(true)

                    val row = adj.second.all { board[it]!!.first }
                    val column = adj.third.all { board[it]!!.first }
                    if (row || column) {
                        val sumOfFalse = board.filter { (_, value) -> !value.first }
                            .map { (key, _) -> key }
                            .sum()
                        return@first sumOfFalse * number
                    }
                }
            }
        }

        throw IllegalStateException("No board won")
    }

    override fun second(data: String): Any {
        val input = data.parse()

        val finishedBoards = mutableSetOf<Int>()

        input.numbers.forEach { number ->
            input.boards.forEachIndexed { index, board ->
                if (finishedBoards.contains(index)) return@forEachIndexed
                if (board.contains(number)) {
                    val adj = board[number]!!
                    board[number] = adj.copy(true)

                    val row = adj.second.all { board[it]!!.first }
                    val column = adj.third.all { board[it]!!.first }
                    if (row || column) {
                        finishedBoards.add(index)
                        if (finishedBoards.size == input.boards.size) {
                            val sumOfFalse = board.filter { (_, value) -> !value.first }
                                .map { (key, _) -> key }
                                .sum()
                            return@second sumOfFalse * number
                        }
                    }
                }
            }
        }

        throw IllegalStateException("No board won")
    }

    private fun String.parse(): Bingo {
        val lines = lines().iterator()
        val numbers = lines.next()
            .split(",")
            .map { it.toInt() }

        val boards = mutableListOf<MutableMap<Int, Triple<Boolean, MutableSet<Int>, MutableSet<Int>>>>()

        while (lines.hasNext()) {
            lines.next() // skip empty line
            val arr = Array(5) { IntArray(5) }
            repeat(5) { rowIndex ->
                lines.next()
                    .split("  ", " ")
                    .filter { it != "" }
                    .forEachIndexed { columnIndex, value ->
                        arr[rowIndex][columnIndex] = value.toInt()
                    }
            }

            val board = mutableMapOf<Int, Triple<Boolean, MutableSet<Int>, MutableSet<Int>>>()
            arr.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, value ->
                    if (!board.contains(value)) {
                        board[value] = Triple(false, mutableSetOf(), mutableSetOf())
                    }

                    val triple = board[value]!!
                    triple.second.addAll(row.toSet())
                    triple.second.remove(value)

                    triple.third.addAll(arr.map { it[columnIndex] })
                    triple.third.remove(value)
                }
            }
            boards.add(board)
        }

        return Bingo(numbers, boards)
    }

    data class Bingo(
        val numbers: List<Int>,
        // node -> (marked, row, column)
        val boards: List<MutableMap<Int, Triple<Boolean, MutableSet<Int>, MutableSet<Int>>>>,
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
