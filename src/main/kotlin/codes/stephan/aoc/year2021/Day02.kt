package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day

object Day02 : Day(2021, 2) {

    override fun first(data: String): Any {
        var horizontal = 0
        var depth = 0

        data.lines()
            .map { it.split(" ") }
            .forEach { (operation, count) ->
                val countInt = count.toInt()
                when (operation) {
                    "down" -> depth += countInt
                    "up" -> depth -= countInt
                    "forward" -> horizontal += countInt
                }
            }

        return horizontal * depth
    }

    override fun second(data: String): Any {
        var horizontal = 0
        var depth = 0
        var aim = 0

        data.lines()
            .map { it.split(" ") }
            .forEach { (operation, count) ->
                val countInt = count.toInt()
                when (operation) {
                    "down" -> aim += countInt
                    "up" -> aim -= countInt
                    "forward" -> {
                        horizontal += countInt
                        depth += (aim * countInt)
                    }
                }
            }

        return horizontal * depth
    }
}

fun main() = Day02.execute()
