package codes.stephan.aoc.common

class Matrix(private val values: Array<Array<Int>>) {

    constructor(n: Int, m: Int, defaultValue: Int) : this(arr2d(n, m, defaultValue))

    fun get(i: Int, j: Int, value: Int): Int {
        return values[i][j]
    }

    fun set(i: Int, j: Int, value: Int) {
        values[i][j] = value
    }

    fun forEach(action: (Point) -> Unit) {
        for (i in values.indices) {
            for (j in values[i].indices) {
                action(Point(i, j, values[i][j]))
            }
        }
    }

    fun forEachNeighbors(i: Int, j: Int, action: (Point) -> Unit, diagonal: Boolean = false) {
        neighbors(i, j, diagonal).forEach {
            action(it)
        }
    }

    fun map(action: (Point) -> Int) {
        for (i in values.indices) {
            for (j in values[i].indices) {
                values[i][j] = action(Point(i, j, values[i][j]))
            }
        }
    }

    fun mapNeighbors(i: Int, j: Int, action: (Point) -> Int, diagonal: Boolean = false) {
        neighbors(i, j, diagonal).forEach {
            values[it.i][it.j] = action(it)
        }
    }

    fun draw() {
        forEach { (i, j, value) ->
            print("$value ")
            if (j + 1 == values[i].size) {
                println()
            }
        }
    }

    private fun neighbors(i: Int, j: Int, diagonal: Boolean): List<Point> {
        return mutableListOf(
            values.safeGet(i - 1, j),
            values.safeGet(i, j - 1),
            values.safeGet(i, j + 1),
            values.safeGet(i + 1, j),
        ).applyIf(diagonal) {
            addAll(
                listOf(
                    values.safeGet(i - 1, j - 1),
                    values.safeGet(i - 1, j + 1),
                    values.safeGet(i + 1, j - 1),
                    values.safeGet(i + 1, j + 1),
                )
            )
        }.filterNotNull()
    }

    private fun Array<Array<Int>>.safeGet(i: Int, j: Int): Point? {
        return if (i in indices && j in this[i].indices) {
            Point(i, j, this[i][j])
        } else {
            null
        }
    }

    data class Point(
        val i: Int,
        val j: Int,
        val value: Int,
    )
}
