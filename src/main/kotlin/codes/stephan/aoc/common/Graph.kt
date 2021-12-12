package codes.stephan.aoc.common

class Graph<T> {

    val adjacencyList: MutableMap<T, MutableSet<T>> = mutableMapOf()

    fun addEdge(source: T, destination: T): Graph<T> {
        adjacencyList
            .computeIfAbsent(source) { mutableSetOf() }
            .add(destination)
        return this
    }

    fun addEdgeUndirected(source: T, target: T): Graph<T> {
        addEdge(source, target)
        addEdge(target, source)
        return this
    }

    fun dfs(source: T, target: T, strategy: VisitStrategy<T> = DefaultVisitStrategy()): List<List<T>> =
        dfs(source, target, mutableListOf(), strategy)

    private fun dfs(source: T, target: T, currentPath: MutableList<T>, strategy: VisitStrategy<T>): List<List<T>> {
        currentPath.add(source)
        strategy.visit(source)

        if (source == target) {
            return listOf(currentPath)
        }

        return adjacencyList.getOrDefault(source, emptySet())
            .filter { strategy.hasNotVisit(it) }
            .flatMap { dfs(it, target, currentPath.toMutableList(), strategy.copy()) }
    }

    interface VisitStrategy<T> {
        fun visit(vertex: T): Boolean
        fun hasNotVisit(vertex: T): Boolean
        fun copy(): VisitStrategy<T>
    }

    class DefaultVisitStrategy<T>(private val visited: MutableSet<T> = mutableSetOf()) : VisitStrategy<T> {
        override fun visit(vertex: T) = visited.add(vertex)
        override fun hasNotVisit(vertex: T) = vertex !in visited
        override fun copy() = DefaultVisitStrategy(visited.toMutableSet())
    }
}
