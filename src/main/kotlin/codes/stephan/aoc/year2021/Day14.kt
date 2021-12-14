package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day

object Day14 : Day(2021, 14) {

    override fun first(data: String): Any {
        return data.parse()
            .simulate(10)
    }

    override fun second(data: String): Any {
        return data.parse()
            .simulate(40)
    }

    private fun Pair<String, Map<String, Char>>.simulate(times: Int): Int {
        val (initialTemplate, mapping) = this
        var template = initialTemplate
        repeat(times) {
            template = template.windowed(2)
                .fold(template.first().toString()) { acc, curr ->
                    acc + mapping[curr] + curr[1]
                }
        }

        val counts = template.groupingBy { it }
            .eachCount()
            .values
            .sorted()

        return counts.last() - counts.first()
    }

    private fun String.parse(): Pair<String, Map<String, Char>> {
        val lines = lines()
        val mapping = lines.subList(2, lines.size)
            .map { it.split(" -> ") }
            .associate { (from, to) -> from to to[0] }
        return Pair(lines.first(), mapping)
    }

    override val test = Test(
        data = "NNCB\n" +
                "\n" +
                "CH -> B\n" +
                "HH -> N\n" +
                "CB -> H\n" +
                "NH -> C\n" +
                "HB -> C\n" +
                "HC -> B\n" +
                "HN -> C\n" +
                "NN -> C\n" +
                "BH -> H\n" +
                "NC -> B\n" +
                "NB -> B\n" +
                "BN -> B\n" +
                "BB -> N\n" +
                "BC -> B\n" +
                "CC -> N\n" +
                "CN -> C",
        firstSolution = 1588,
        secondSolution = 2188189693529,
    )
}

fun main() = Day14.execute()
