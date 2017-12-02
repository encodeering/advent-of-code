package com.encodeering.aoc.y2017.d2

import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d2/checksum.txt") {
        println ("checksum #1: ${checksum (it)}")
    }

    traverse ("/y2017/d2/checksum.txt") {
        println ("checksum #2: ${checksum2 (it)}")
    }
}

fun checksum (text : Sequence<String>) : Int {
    val    rows = text.map { it.split ("\\s".toRegex ()).map { it.toInt () } }

    return rows.map {
        it.fold (Int.MAX_VALUE to Int.MIN_VALUE) {
            pair,                                 value ->
            pair.copy (first = minOf (pair.first, value), second = maxOf (pair.second, value))
        }
    }.sumBy { it.second - it.first }
}

fun checksum2 (text : Sequence<String>) : Int {
    val    rows = text.map { it.split ("\\s".toRegex ()).map { it.toInt () } }

    return rows.map {
        it.candidates ().map {
            it.copy (
                maxOf (it.first, it.second),
                minOf (it.first, it.second)
            )
        }.first { it.first % it.second == 0 }
    }.sumBy     { it.first / it.second }
}

private fun <T> List<T>.candidates() : List<Pair<T, T>> {
    return (0 until size).flatMap {
        val                 head = get (it)
        drop (it + 1).map { head to it }
    }
}