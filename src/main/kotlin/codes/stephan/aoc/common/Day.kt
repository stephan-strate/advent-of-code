package codes.stephan.aoc.common

import java.io.File
import java.net.URL
import kotlin.system.measureTimeMillis

abstract class Day(private val year: Int, private val day: Int) {

    /**
     * Personal input data fetched from adventofcode.com using the
     * authentication token.
     * The data will be cached in the build directory to throttle the access
     * to the page (the site is under heavy load anyways) and provide faster
     * turnarounds.
     */
    private val data: String = Fetcher.fetchInput(year, day)

    // implementation for the day
    abstract fun first(data: String): Any
    abstract fun second(data: String): Any

    /**
     * Execute the whole implementation (first and second part).
     * Measures the time it takes to calculate the actual solution on personal
     * input data. Also asserts that the test data is always working.
     */
    fun execute() {
        println("Year $year, Day $day")

        check(first(test.data) == test.firstSolution) { "First solution failed on test data" }
        measureTimeMillis {
            print("First: ${first(data)}")
        }.run {
            println(" (${this}ms)")
        }

        check(second(test.data) == test.secondSolution) { "Second solution failed on test data" }
        measureTimeMillis {
            print("Second: ${second(data)}")
        }.run {
            println(" (${this}ms)")
        }
    }

    /**
     * Test data will be asserted automatically to continuously make
     * sure the solution works.
     * Easy way to provide test data permanently without manually switching to
     * personal test data.
     */
    abstract val test: Test

    data class Test(
        val data: String,
        val firstSolution: Any,
        val secondSolution: Any,
    )
}

object Fetcher {

    private val cookies = mapOf(
        "session" to File(".cookie")
            .readText()
            .trim()
    )

    fun fetchInput(year: Int, day: Int): String {
        val cacheKey = "$year-$day".md5()
        val cachedFile = File("build/resources/main/$cacheKey")

        if (cachedFile.exists()) {
            println("=> Accessing cached input data")
            return cachedFile
                .readText()
        } else {
            println("=> Fetching input data from adventofcode.com")
            val connection = URL("https://adventofcode.com/$year/day/$day/input").openConnection()
            connection.setRequestProperty("Cookie", cookies.asCookieString())
            connection.connect()

            val value = connection
                .getInputStream()
                .bufferedReader()
                .readText()
                .trim()
            cachedFile.writeText(value)
            return value
        }
    }

    private fun Map<String, String>.asCookieString() =
        entries.joinToString(separator = ";") { (k, v) -> "$k=$v" }
}
