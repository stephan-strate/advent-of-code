fun main() {

    fun parse(input: List<String>): List<Int> {
        return input.map { it.toInt() }
    }

    fun increasedCount(input: List<Int>): Int {
        return input.windowed(2)
            .filter { it.size == 2 }
            .map { (left, right) -> left < right }
            .sumOf { it.toInt() }
    }

    fun part1(input: List<String>): Int {
        val parsed = parse(input)
        return increasedCount(parsed)
    }

    fun part2(input: List<String>): Int {
        val parsed = parse(input)
            .windowed(3)
            .filter { it.size == 3 }
            .map { it.sum() }
        return increasedCount(parsed)
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
