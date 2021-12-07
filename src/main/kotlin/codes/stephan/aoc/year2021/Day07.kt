package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.gauss
import codes.stephan.aoc.common.ints
import kotlin.math.abs

object Day07 : Day(2021, 7) {

    override fun first(data: String): Any {
        val input = data.ints()
        val max = input.maxOf { it }

        return (0..max).map { i ->
            input.sumOf { abs(it - i) }
        }.minOf { it }
    }

    override fun second(data: String): Any {
        val input = data.ints()
        val max = input.maxOf { it }

        return (0..max).map { i ->
            input.sumOf { abs(it - i).gauss() }
        }.minOf { it }
    }

    override val test = Test(
        data = "16,1,2,0,4,2,7,1,2,14",
        firstSolution = 37,
        secondSolution = 168,
    )
}

fun main() = Day07.execute()
