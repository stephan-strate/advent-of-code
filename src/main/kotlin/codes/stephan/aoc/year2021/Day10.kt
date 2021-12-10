package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day

object Day10 : Day(2021, 10) {

    override fun first(data: String): Any {
        return data.analyseSyntax()
            .filter { it.third }
            .sumOf { (_, close, _) ->
                Characters.syntaxCheckerScore(close)
            }
    }

    override fun second(data: String): Any {
        val scores = data.analyseSyntax()
            .filter { !it.third }
            .map { (stack, _, _) ->
                stack.reversed()
                    .map { Characters.autocompleteScore(it) }
                    .fold(0L) { acc, curr -> (acc * 5) + curr }
            }
            .sorted()

        return scores[scores.size / 2]
    }

    private fun String.analyseSyntax(): List<Triple<ArrayDeque<Char>, Char, Boolean>> {
        return lines()
            .map { chunk ->
                val stack = ArrayDeque<Char>(0)
                chunk.toCharArray().forEach { char ->
                    if (Characters.isOpen(char)) {
                        stack.addLast(char)
                    } else if (Characters.isClose(char)) {
                        if (Characters.matches(stack.last(), char)) {
                            stack.removeLast()
                        } else {
                            return@map Triple(stack, char, true)
                        }
                    } else {
                        throw IllegalArgumentException("Unknown character $char")
                    }
                }

                return@map Triple(stack, '/', false)
            }
    }

    enum class Characters(val open: Char, val close: Char, val syntaxCheckerScore: Int, val autocompleteScore: Int) {
        ROUND_BRACKET('(', ')', 3, 1),
        SQUARE_BRACKET('[', ']', 57, 2),
        CURLY_BRACKET('{', '}', 1197, 3),
        ANGLE_BRACKET('<', '>', 25137, 4);

        companion object {

            fun syntaxCheckerScore(close: Char): Int {
                return values()
                    .find { it.close == close }!!
                    .syntaxCheckerScore
            }

            fun autocompleteScore(open: Char): Int {
                return values()
                    .find { it.open == open }!!
                    .autocompleteScore
            }

            fun matches(open: Char, close: Char): Boolean {
                return values()
                    .find { it.open == open }!!
                    .close == close
            }

            fun isOpen(char: Char): Boolean {
                return values()
                    .map { it.open }
                    .contains(char)
            }

            fun isClose(char: Char): Boolean {
                return values()
                    .map { it.close }
                    .contains(char)
            }
        }
    }

    override val test = Test(
        data = "[({(<(())[]>[[{[]{<()<>>\n" +
                "[(()[<>])]({[<{<<[]>>(\n" +
                "{([(<{}[<>[]}>{[]{[(<()>\n" +
                "(((({<>}<{<{<>}{[]{[]{}\n" +
                "[[<[([]))<([[{}[[()]]]\n" +
                "[{[{({}]{}}([{[{{{}}([]\n" +
                "{<[[]]>}<{[{[{[]{()[[[]\n" +
                "[<(<(<(<{}))><([]([]()\n" +
                "<{([([[(<>()){}]>(<<{{\n" +
                "<{([{{}}[<[[[<>{}]]]>[]]",
        firstSolution = 26397,
        secondSolution = 288957L,
    )
}

fun main() = Day10.execute()
