package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import java.util.*

object Day09 : Day(2021, 9) {

    override fun first(data: String): Any {
        val input = data.parse()
        val lowPoints = mutableListOf<Int>()

        input.forEachIndexed { i, ints ->
            ints.forEachIndexed { j, value ->
                if (input.getAdjacents(i, j).all { it > value }) {
                    lowPoints.add(value)
                }
            }
        }

        return lowPoints.sumOf { it + 1 }
    }

    override fun second(data: String): Any {
        val input = data.parse()
        val basins = mutableListOf<Int>()

        input.forEachIndexed { i, ints ->
            ints.forEachIndexed { j, value ->
                if (input.getAdjacents(i, j).all { it > value }) {
                    basins.add(input.getBasin(i, j))
                }
            }
        }

        basins.sortByDescending { it }

        return basins.take(3)
            .fold(1) { acc, curr -> acc * curr }
    }

    private fun List<List<Int>>.getBasin(i: Int, j: Int): Int {
        val basin = mutableSetOf<Pair<Int, Int>>()
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(Pair(i, j))

        while (queue.isNotEmpty()) {
            val point = queue.poll()
            val candidates = getPoints(point.first, point.second)
                .filter { it !in basin }
                .filter { get(it.first)[it.second] != 9 }
            basin.addAll(candidates)
            queue.addAll(candidates)
        }

        return basin.size
    }

    private fun List<List<Int>>.getPoints(i: Int, j: Int): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()

        if (i > 0) points.add(Pair(i - 1, j))
        if (j > 0) points.add(Pair(i, j - 1))
        if (j < get(i).size - 1) points.add(Pair(i, j + 1))
        if (i < size - 1) points.add(Pair(i + 1, j))

        return points
    }

    private fun List<List<Int>>.getAdjacents(i: Int, j: Int): List<Int> {
        val adjacencyList = mutableListOf<Int>()

        if (i > 0) adjacencyList.add(get(i - 1)[j])
        if (j > 0) adjacencyList.add(get(i)[j - 1])
        if (j < get(i).size - 1) adjacencyList.add(get(i)[j + 1])
        if (i < size - 1) adjacencyList.add(get(i + 1)[j])

        return adjacencyList
    }

    private fun String.parse(): List<List<Int>> {
        return lines()
            .map { it.toCharArray().map { it.digitToInt() } }
    }

    override val test = Test(
        data = "2199943210\n" +
                "3987894921\n" +
                "9856789892\n" +
                "8767896789\n" +
                "9899965678",
        firstSolution = 15,
        secondSolution = 1134,
    )
}

fun main() = Day09.execute()
