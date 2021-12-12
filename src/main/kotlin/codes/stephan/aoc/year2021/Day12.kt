package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.Graph
import codes.stephan.aoc.common.isLowercase

object Day12 : Day(2021, 12) {

    val STOP_KEYS = listOf("start", "end")

    override fun first(data: String): Any {
        return data.parse()
            .dfs("start", "end", LowercaseVisitStrategy())
            .size
    }

    override fun second(data: String): Any {
        return data.parse()
            .dfs("start", "end", LowercasePlusVisitStrategy())
            .size
    }

    private fun String.parse(): Graph<String> {
        return lines()
            .map { it.split("-") }
            .fold(Graph()) { graph, (source, target) -> graph.addEdgeUndirected(source, target) }
    }

    class LowercaseVisitStrategy(private val visited: MutableSet<String> = mutableSetOf()) : Graph.VisitStrategy<String> {
        override fun visit(vertex: String) = if (vertex.isLowercase()) visited.add(vertex) else false
        override fun hasNotVisit(vertex: String) = vertex !in visited
        override fun copy() = LowercaseVisitStrategy(visited.toMutableSet())
    }

    class LowercasePlusVisitStrategy(private val visited: MutableMap<String, Int> = mutableMapOf()) : Graph.VisitStrategy<String> {
        override fun visit(vertex: String) = if (vertex.isLowercase()) {
            visited[vertex] = visited.computeIfAbsent(vertex) { 0 } + 1
            true
        } else {
            false
        }

        override fun hasNotVisit(vertex: String) = vertex !in visited || (vertex !in STOP_KEYS && vertex.isLowercase()
                && visited.filter { (key, _) -> key !in STOP_KEYS }.all { (_, value) -> value < 2 })

        override fun copy() = LowercasePlusVisitStrategy(visited.toMutableMap())
    }

    override val test = Test(
        data = "start-A\n" +
                "start-b\n" +
                "A-c\n" +
                "A-b\n" +
                "b-d\n" +
                "A-end\n" +
                "b-end",
        firstSolution = 10,
        secondSolution = 36,
    )
}

fun main() = Day12.execute()
