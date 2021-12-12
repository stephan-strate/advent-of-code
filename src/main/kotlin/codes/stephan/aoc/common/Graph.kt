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

    private fun <T> Graph<T>.dfs(source: T, target: T): Set<List<T>> {
        val paths = mutableSetOf<List<T>>()
        dfs(source, target, mutableSetOf(), mutableListOf(), paths)
        return paths
    }

    private fun <T> Graph<T>.dfs(source: T, target: T, visited: MutableSet<T>, currentPath: MutableList<T>, finishedPaths: MutableSet<List<T>>) {
        currentPath.add(source)
        visited.add(source)

        if (source == target) {
            finishedPaths.add(currentPath)
            return
        }

        adjacencyList.getOrDefault(source, emptySet())
            .forEach { x ->
                if (x !in visited) {
                    dfs(x, target, visited.toMutableSet(), currentPath.toMutableList(), finishedPaths)
                }
            }
    }
}
