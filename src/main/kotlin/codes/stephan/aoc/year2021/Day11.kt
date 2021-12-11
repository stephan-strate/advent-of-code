package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.matrix

object Day11 : Day(2021, 11) {

    override fun first(data: String): Any {
        return data.matrix()
            .simulate(100)
    }

    override fun second(data: String): Any {
        var steps = 0
        var allFlashed = false
        val input = data.matrix()

        do {
            steps++
            val flashes = input.simulate(1)
            if (flashes == 100) {
                allFlashed = true
            }
        } while (!allFlashed)

        return steps
    }

    private fun Array<Array<Int>>.simulate(n: Int): Int {
        var flashes = 0

        // simulate energy levels for 100 steps
        repeat(n) {
            // increase value for all octopus
            for (i in indices) {
                for (j in this[i].indices) {
                    this[i][j]++
                }
            }

            // flash octopus
            var flashed: Boolean
            do {
                flashed = false
                for (i in indices) {
                    for (j in this[i].indices) {
                        if (this[i][j] == 10) {
                            this[i][j] = 0
                            flashed = true
                            flashes++

                            increaseAdjacent(i, j)
                        }
                    }
                }
            } while (flashed)
        }

        return flashes
    }

    private fun Array<Array<Int>>.increaseAdjacent(i: Int, j: Int) {
        // upper row
        if (i > 0) {
            increase(i - 1, j)
            if (j > 0) increase(i - 1, j - 1)
            if (j + 1 < this[i - 1].size) increase(i - 1, j + 1)
        }

        if (j > 0) increase(i, j - 1)
        if (j + 1 < this[i].size) increase(i, j + 1)

        // lower row
        if (i + 1 < size) {
            increase(i + 1, j)
            if (j > 0) increase(i + 1, j - 1)
            if (j + 1 < this[i + 1].size) increase(i + 1, j + 1)
        }
    }

    private fun Array<Array<Int>>.increase(i: Int, j: Int) {
        if (this[i][j] in 1..9) {
            this[i][j]++
        }
    }

    override val test = Test(
        data = "5483143223\n" +
                "2745854711\n" +
                "5264556173\n" +
                "6141336146\n" +
                "6357385478\n" +
                "4167524645\n" +
                "2176841721\n" +
                "6882881134\n" +
                "4846848554\n" +
                "5283751526",
        firstSolution = 1656,
        secondSolution = 195,
    )
}

fun main() = Day11.execute()
