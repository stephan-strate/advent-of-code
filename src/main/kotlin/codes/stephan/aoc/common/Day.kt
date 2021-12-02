package codes.stephan.aoc.common

import java.io.File
import java.net.URL
import kotlin.system.measureTimeMillis

abstract class Day(private val year: Int, private val day: Int) {

    protected open var data: String = Fetcher.fetchInput(year, day)

    // implementation for the day
    abstract fun first(data: String): Any
    abstract fun second(data: String): Any

    fun execute() {
        println("Year $year, Day $day")

        measureTimeMillis {
            println("First: ${first(data)}")
        }.run {
            println("Time: ${this}ms")
        }

        measureTimeMillis {
            println("Second: ${second(data)}")
        }.run {
            println("Time: ${this}ms")
        }
    }
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
            println("=> Fetching input data")
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
