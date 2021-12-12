package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.Matrix
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

    private fun Matrix.simulate(n: Int): Int {
        var flashes = 0

        repeat(n) {
            map {
                it.value + 1
            }

            var flashed: Boolean
            do {
                flashed = false

                map { (i, j, value) ->
                    if (value == 10) {
                        mapNeighbors(i, j, {
                            if (it.value in 1..9) {
                                it.value + 1
                            } else {
                                it.value
                            }
                        }, true)

                        flashed = true
                        flashes++
                        0
                    } else {
                        value
                    }
                }
            } while (flashed)
        }

        return flashes
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
