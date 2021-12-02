package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.year2021.Day02.Operation.*

object Day02 : Day(2021, 2) {

    override fun first(data: String): Any {
        return data.parse()
            .fold(Pair(0, 0)) { (horizontal, depth), (operation, count) ->
                when (operation) {
                    DOWN -> Pair(horizontal, depth + count)
                    UP -> Pair(horizontal, depth - count)
                    FORWARD -> Pair(horizontal + count, depth)
                }
            }
            .let { (horizontal, depth) -> horizontal * depth }
    }

    override fun second(data: String): Any {
        return data.parse()
            .fold(Triple(0, 0, 0)) { (horizontal, depth, aim), (operation, count) ->
                when (operation) {
                    DOWN -> Triple(horizontal, depth, aim + count)
                    UP -> Triple(horizontal, depth, aim - count)
                    FORWARD -> Triple(horizontal + count, depth + aim * count, aim)
                }
            }
            .let { (horizontal, depth, _) -> horizontal * depth }
    }

    private fun String.parse() = lines()
        .map { it.split(" ") }
        .map { (operation, count) -> Operation.valueOf(operation.uppercase()) to count.toInt() }

    enum class Operation {
        DOWN, UP, FORWARD
    }

    override val test = Test(
        data = "forward 5\n" +
                "down 5\n" +
                "forward 8\n" +
                "up 3\n" +
                "down 8\n" +
                "forward 2",
        firstSolution = 150,
        secondSolution = 900,
    )
}

fun main() = Day02.execute()
