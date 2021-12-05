package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import kotlin.math.max
import kotlin.math.min

object Day05 : Day(2021, 5) {

    override fun first(data: String): Any {
        val pattern = Regex("""(\d*),(\d*) -> (\d*),(\d*)""")
        val ob = data.lines()
            .map { line ->
                val match = pattern.matchEntire(line)
                check(match != null) { "Can not deserialize coordinates" }

                val (x1, y1, x2, y2) = match.destructured
                Rectangle(
                    Coordinate(x1.toInt(), y1.toInt()),
                    Coordinate(x2.toInt(), y2.toInt()),
                )
            }

        val highestCoordinate = ob.fold(Pair(0, 0)) { acc, curr ->
            Pair(
                max(acc.first, max(curr.first.x, curr.second.x)),
                max(acc.second, max(curr.first.y, curr.second.y)),
            )
        }

        val matrix = Array(highestCoordinate.second + 1) { IntArray(highestCoordinate.first + 1) { 0 } }

        ob.forEach { coordinates ->
            if (coordinates.first.x == coordinates.second.x && coordinates.first.y != coordinates.second.y) {
                // vertical line
                val x = coordinates.first.x
                val lower = min(coordinates.first.y, coordinates.second.y)
                val upper = max(coordinates.first.y, coordinates.second.y)
                for (y in lower..upper) {
                    matrix[y][x]++
                }
            } else if (coordinates.first.y == coordinates.second.y && coordinates.first.x != coordinates.second.x) {
                // horizontal line
                val y = coordinates.first.y
                val lower = min(coordinates.first.x, coordinates.second.x)
                val upper = max(coordinates.first.x, coordinates.second.x)
                for (x in lower..upper) {
                    matrix[y][x]++
                }
            } else {
                // diagonal line
            }
        }

        var maxCount = 0
        matrix.forEach { row ->
            row.forEach { v ->
                if (v > 1) {
                    maxCount++
                }
            }
        }

        return maxCount
    }

    override fun second(data: String): Any {
        val pattern = Regex("""(\d*),(\d*) -> (\d*),(\d*)""")
        val ob = data.lines()
            .map { line ->
                val match = pattern.matchEntire(line)
                check(match != null) { "Can not deserialize coordinates" }

                val (x1, y1, x2, y2) = match.destructured
                Rectangle(
                    Coordinate(x1.toInt(), y1.toInt()),
                    Coordinate(x2.toInt(), y2.toInt()),
                )
            }

        val highestCoordinate = ob.fold(Pair(0, 0)) { acc, curr ->
            Pair(
                max(acc.first, max(curr.first.x, curr.second.x)),
                max(acc.second, max(curr.first.y, curr.second.y)),
            )
        }

        val matrix = Array(highestCoordinate.second + 1) { IntArray(highestCoordinate.first + 1) { 0 } }

        ob.forEach { coordinates ->
            if (coordinates.first.x == coordinates.second.x && coordinates.first.y != coordinates.second.y) {
                // vertical line
                val x = coordinates.first.x
                val lower = min(coordinates.first.y, coordinates.second.y)
                val upper = max(coordinates.first.y, coordinates.second.y)
                for (y in lower..upper) {
                    matrix[y][x]++
                }
            } else if (coordinates.first.y == coordinates.second.y && coordinates.first.x != coordinates.second.x) {
                // horizontal line
                val y = coordinates.first.y
                val lower = min(coordinates.first.x, coordinates.second.x)
                val upper = max(coordinates.first.x, coordinates.second.x)
                for (x in lower..upper) {
                    matrix[y][x]++
                }
            } else {
                // diagonal line
                if (coordinates.first.x < coordinates.second.x) {
                    if (coordinates.first.y < coordinates.second.y) {
                        var currX = coordinates.first.x
                        var currY = coordinates.first.y
                        while (currX != coordinates.second.x + 1) {
                            matrix[currY][currX]++
                            currX++
                            currY++
                        }
                    } else {
                        var currX = coordinates.first.x
                        var currY = coordinates.first.y
                        while (currX != coordinates.second.x + 1) {
                            matrix[currY][currX]++
                            currX++
                            currY--
                        }
                    }
                } else {
                    if (coordinates.first.y < coordinates.second.y) {
                        var currX = coordinates.first.x
                        var currY = coordinates.first.y
                        while (currX != coordinates.second.x - 1) {
                            matrix[currY][currX]++
                            currX--
                            currY++
                        }
                    } else {
                        var currX = coordinates.first.x
                        var currY = coordinates.first.y
                        while (currX != coordinates.second.x - 1) {
                            matrix[currY][currX]++
                            currX--
                            currY--
                        }
                    }
                }
            }
        }

        var maxCount = 0
        matrix.forEach { row ->
            row.forEach { v ->
                if (v > 1) {
                    maxCount++
                }
            }
        }

        return maxCount
    }

    data class Rectangle(
        val first: Coordinate,
        val second: Coordinate,
    )

    data class Coordinate(
        val x: Int,
        val y: Int,
    )

    override val test = Test(
        data = "0,9 -> 5,9\n" +
                "8,0 -> 0,8\n" +
                "9,4 -> 3,4\n" +
                "2,2 -> 2,1\n" +
                "7,0 -> 7,4\n" +
                "6,4 -> 2,0\n" +
                "0,9 -> 2,9\n" +
                "3,4 -> 1,4\n" +
                "0,0 -> 8,8\n" +
                "5,5 -> 8,2",
        firstSolution = 5,
        secondSolution = 12,
    )
}

fun main() = Day05.execute()
