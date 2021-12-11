package codes.stephan.aoc.common

import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun String.intLines(): List<Int> = lines().map { it.toInt() }

fun String.ints(delimiter: String = ",") = split(delimiter).map { it.toInt() }

fun String.matrix(): Array<Array<Int>> = lines()
    .map { line -> line.toCharArray().map { it.digitToInt() }.toTypedArray() }
    .toTypedArray()
