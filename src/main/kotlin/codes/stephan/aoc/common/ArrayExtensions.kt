package codes.stephan.aoc.common

// https://stackoverflow.com/a/63409789/6382398
inline fun <reified T> Array<T>.rotate(n: Int): Array<T> {
    var distance = n
    if (n < 0) distance += size
    return Array(size) { this[(it + distance) % size] }
}

inline fun <reified T> arr2d(n: Int, m: Int, defaultValue: T): Array<Array<T>> {
    return Array(n) { Array(m) { defaultValue } }
}
