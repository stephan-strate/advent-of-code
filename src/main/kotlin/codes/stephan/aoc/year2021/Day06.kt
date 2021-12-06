package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.ints
import codes.stephan.aoc.common.rotate

object Day06 : Day(2021, 6) {

    override fun first(data: String): Any {
        return data.calculatePopulation(80)
    }

    override fun second(data: String): Any {
        return data.calculatePopulation(256)
    }

    private fun String.calculatePopulation(days: Int): Long {
        var timers = ints().fold(Array<Long>(9) { 0 }) { state, timer ->
            state[timer]++
            state
        }

        repeat(days) {
            timers = timers.rotate(1)
            timers[6] += timers[8]
        }

        return timers.sum()
    }

    override val test = Test(
        data = "3,4,3,1,2",
        firstSolution = 5934L,
        secondSolution = 26984457539L,
    )
}

fun main() = Day06.execute()
