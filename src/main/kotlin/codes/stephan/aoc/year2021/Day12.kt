package codes.stephan.aoc.year2021

import codes.stephan.aoc.common.Day
import codes.stephan.aoc.common.Graph
import codes.stephan.aoc.common.isLowercase

object Day12 : Day(2021, 12) {

    override fun first(data: String): Any {
        return data.parse()
            .search("start", "end", false)
            .size
    }

    override fun second(data: String): Any {
        val x = data
            .replace("start", "START")
            .replace("end", "END")
            .parse()
            .search("START", "END", true)

        return x.size
    }

    private fun String.parse(): Graph<String> {
        return lines()
            .map { it.split("-") }
            .fold(Graph()) { graph, (source, target) -> graph.addEdgeUndirected(source, target) }
    }

    private fun <T> Graph<T>.search(source: T, target: T, smallCaves: Boolean): Set<List<T>> {
        val paths = mutableSetOf<List<T>>()
        search(source, target, mutableSetOf(), mutableListOf(), paths, !smallCaves)
        return paths
    }

    private fun <T> Graph<T>.search(source: T, target: T, visited: MutableSet<T>, currentPath: MutableList<T>, finishedPaths: MutableSet<List<T>>, visitedSmallCave: Boolean) {
        currentPath.add(source)

        if (source.toString().isLowercase() || source == "START" || source == "END") {
            visited.add(source)
        }

        if (source == target) {
            finishedPaths.add(currentPath)
            return
        }

        adjacencyList.getOrDefault(source, emptySet())
            .forEach { x ->
                if (x !in visited || (!visitedSmallCave && x.toString().isLowercase())) {
                    search(
                        x,
                        target,
                        visited.toMutableSet(),
                        currentPath.toMutableList(),
                        finishedPaths,
                        visitedSmallCave || (x.toString().isLowercase() && x in visited)
                    )
                }
            }
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
