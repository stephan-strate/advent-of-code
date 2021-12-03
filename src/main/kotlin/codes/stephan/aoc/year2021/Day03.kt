package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day

object Day03 : Day(2021, 3) {

    override fun first(data: String): Any {
        val input = data.lines()
        val range = input.first().indices
        val (gamma, epsilon) = range.fold(Pair("", "")) { (partialGamma, partialEpsilon), index ->
            val (zeros, ones) = input.countBits(index)
            Pair(partialGamma + zeroOrOne(zeros > ones), partialEpsilon + zeroOrOne(zeros < ones))
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    override fun second(data: String): Any {
        val input = data.lines()
        val range = input.first().indices
        val oxygen = range.fold(input) { numbers, index ->
            val (zeros, ones) = numbers.countBits(index)
            val mostCommon = zeroOrOne(ones < zeros)
            if (numbers.size == 1) return@fold numbers
            numbers.filter { it[index] == mostCommon }
        }.joinToString("")

        val co2 = range.fold(input) { numbers, index ->
            val (zeros, ones) = numbers.countBits(index)
            val leastCommon = zeroOrOne(ones >= zeros)
            if (numbers.size == 1) return@fold numbers
            numbers.filter { it[index] == leastCommon }
        }.joinToString("")

        return oxygen.toInt(2) * co2.toInt(2)
    }

    private fun zeroOrOne(condition: Boolean) = if (condition) '1' else '0'

    private fun List<String>.countBits(pos: Int): Pair<Int, Int> {
        val groups = map { it[pos] }
            .joinToString("")
            .groupingBy { it }
            .eachCount()

        return Pair(
            groups.getOrDefault('0', 0),
            groups.getOrDefault('1', 0)
        )
    }

    override val test = Test(
        data = "00100\n" +
                "11110\n" +
                "10110\n" +
                "10111\n" +
                "10101\n" +
                "01111\n" +
                "00111\n" +
                "11100\n" +
                "10000\n" +
                "11001\n" +
                "00010\n" +
                "01010",
        firstSolution = 198,
        secondSolution = 230,
    )
}

fun main() = Day03.execute()
