package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.intLines
import codes.stephan.aoc.common.toInt

object Day01 : Day(2021, 1) {

    override fun first(data: String): Any {
        return increasedCount(
            data.intLines()
        )
    }

    override fun second(data: String): Any {
        return increasedCount(
            data.intLines()
                .windowed(3)
                .map { it.sum() }
        )
    }

    private fun increasedCount(input: List<Int>): Int {
        return input.windowed(2)
            .map { (left, right) -> left < right }
            .sumOf { it.toInt() }
    }
}

fun main() = Day01.execute()
