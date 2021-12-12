package codes.stephan.aoc.common

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T = apply {
    if (condition) block(this)
}
