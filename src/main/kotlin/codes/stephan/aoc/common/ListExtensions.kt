package codes.stephan.aoc.common

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val result: MutableList<MutableList<T>> = mutableListOf()
    for (i in first().indices) {
        val col = mutableListOf<T>()
        for (row in this) {
            col.add(row[i])
        }
        result.add(col)
    }

    return result
}
