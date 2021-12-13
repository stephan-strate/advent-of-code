package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.Matrix

object Day13 : Day(2021, 13) {

    override fun first(data: String): Any {
        return data.parse()
            .fold(1)
            .coordinates
            .distinct()
            .size
    }

    override fun second(data: String): Any {
        val foldedPaper = data.parse()
            .fold(Int.MAX_VALUE)

        val matrix = Matrix(6, 40, 1)
        foldedPaper.coordinates.distinct().forEach { (x, y) ->
            matrix.set(y, x, 0)
        }
        matrix.draw()

        return -1
    }

    private fun Origami.fold(instructionsCount: Int): Origami {
        instructions.take(instructionsCount).forEach { (direction, lineNumber) ->
            coordinates = coordinates.map { (x, y) ->
                when (direction) {
                    "x" -> if (x > lineNumber) Pair(lineNumber - (x - lineNumber), y) else Pair(x, y)
                    "y" -> if (y > lineNumber) Pair(x, lineNumber - (y - lineNumber)) else Pair(x, y)
                    else -> throw IllegalArgumentException("Unknown direction $direction")
                }
            }
        }

        return this
    }

    private fun String.parse(): Origami {
        val coordinates = mutableListOf<Pair<Int, Int>>()
        val lines = lines().iterator()
        while (lines.hasNext()) {
            val line = lines.next()
            if (line.isEmpty()) break
            val (x, y) = line.split(",")
            coordinates.add(Pair(x.toInt(), y.toInt()))
        }

        val instructions = mutableListOf<Instruction>()
        val regex = Regex("fold along (.)=(\\d*)")
        while (lines.hasNext()) {
            val line = lines.next()
            val match = regex.matchEntire(line)
            check(match != null)
            val (direction, lineNumber) = match.destructured
            instructions.add(Instruction(direction, lineNumber.toInt()))
        }

        return Origami(coordinates, instructions)
    }

    data class Origami(
        var coordinates: List<Pair<Int, Int>>,
        val instructions: List<Instruction>,
    )

    data class Instruction(
        val direction: String,
        val line: Int,
    )

    override val test = Test(
        data = "6,10\n" +
                "0,14\n" +
                "9,10\n" +
                "0,3\n" +
                "10,4\n" +
                "4,11\n" +
                "6,0\n" +
                "6,12\n" +
                "4,1\n" +
                "0,13\n" +
                "10,12\n" +
                "3,4\n" +
                "3,0\n" +
                "8,4\n" +
                "1,10\n" +
                "2,14\n" +
                "8,10\n" +
                "9,0\n" +
                "\n" +
                "fold along y=7\n" +
                "fold along x=5",
        firstSolution = 17,
        secondSolution = -1,
    )
}

fun main() = Day13.execute()
