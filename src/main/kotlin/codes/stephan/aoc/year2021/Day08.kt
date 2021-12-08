package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.year2021.Day08.Digit.*

object Day08 : Day(2021, 8) {

    override fun first(data: String): Any {
        return data.parse()
            .flatMap { it.output }
            .count { it.length in Digit.distinctSegments() }
    }

    override fun second(data: String): Any {
        return data.parse()
            .map { (digits, output) ->
                val mapping = mutableMapOf<Char, Char>()
                mapping['a'] = (digits.find(SEVEN) - digits.find(ONE)).single()

                val fivers = digits.findAll(5)
                val fiversHandled = fivers
                    .map { (it - mapping['a']!!) - digits.find(FOUR) }

                val two = fivers[fiversHandled.indexOf(fiversHandled.find { it.length == 2 }!!)]

                mapping['g'] = fiversHandled.find { it.length == 1 }!!.single()
                mapping['b'] = (digits.find(FOUR) - digits.find(ONE) - two).single()
                mapping['f'] = (digits.find(SEVEN) - two).single()
                mapping['c'] = (digits.find(ONE) - (digits.find(SEVEN) - two)).single()
                mapping['e'] = (two - digits.find(FOUR) - mapping['g']!! - mapping['a']!!).single()
                mapping['d'] = ((((((digits.find(EIGHT) - mapping['a']!!) - mapping['b']!!) - mapping['c']!!) - mapping['e']!!) - mapping['f']!!) - mapping['g']!!).single()

                Pair(mapping, output)
            }
            .map { (mapping, output) ->
                output.map { digit ->
                    val s = digit.toCharArray()
                    mapping.forEach {
                        val index = digit.indexOf(it.value)
                        if (index != -1) {
                            s[index] = it.key
                        }
                    }
                    s.joinToString("")
                }
            }
            .sumOf {
                it.map { Digit.findDigit(it) }
                    .map { it.number }
                    .joinToString("")
                    .toInt()
            }
    }

    private fun String.parse(): List<IO> {
        return lines()
            .map { it.split(" | ") }
            .map { (input, output) -> IO(input.split(" "), output.split(" ")) }
    }

    private operator fun String.minus(other: String): String = (toSet() - other.toSet()).joinToString("")
    private operator fun String.minus(other: Char): String = (toSet() - other).joinToString("")

    private fun List<String>.find(digit: Digit): String = find { it.length == digit.segments }!!
    private fun List<String>.findAll(segments: Int): List<String> = filter { it.length == segments }

    enum class Digit(val number: Int, val segments: Int, val chars: String, val distinct: Boolean = false) {
        ZERO(0, 6, "abcefg"),
        ONE(1, 2, "cf", true),
        TWO(2, 5, "acdeg"),
        THREE(3, 5, "acdfg"),
        FOUR(4, 4, "bcdf", true),
        FIVE(5, 5, "abdfg"),
        SIX(6, 6, "abdefg"),
        SEVEN(7, 3, "acf", true),
        EIGHT(8, 7, "abcdefg", true),
        NINE(9, 6, "abcdfg");

        companion object {
            fun findDigit(str: String): Digit {
                return values().find {
                    it.chars.toSet() == str.toSet()
                }!!
            }

            fun distinctDigits() = values()
                .filter { it.distinct }

            fun distinctSegments() = distinctDigits()
                .map { it.segments }
        }
    }

    data class IO(
        val input: List<String>,
        val output: List<String>,
    )

    override val test = Test(
        data = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\n" +
                "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\n" +
                "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\n" +
                "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\n" +
                "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\n" +
                "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\n" +
                "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\n" +
                "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\n" +
                "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\n" +
                "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce",
        firstSolution = 26,
        secondSolution = 61229,
    )
}

fun main() = Day08.execute()
