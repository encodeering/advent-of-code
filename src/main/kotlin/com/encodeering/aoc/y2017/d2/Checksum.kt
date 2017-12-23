package com.encodeering.aoc.y2017.d2

import com.encodeering.aoc.common.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    traverse ("/y2017/d2/checksum.txt") {
        println ("checksum #1: ${checksum1 (it)}")
    }

    traverse ("/y2017/d2/checksum.txt") {
        println ("checksum #2: ${checksum2 (it)}")
    }
}

fun checksum1 (text : Sequence<String>) : Int {
    return checksum (text) {
        fold (Int.MAX_VALUE to Int.MIN_VALUE) {
            pair,                                 value ->
            pair.copy (first = minOf (pair.first, value), second = maxOf (pair.second, value))
        }
    }.sumBy { it.second - it.first }
}

fun checksum2 (text : Sequence<String>) : Int {
    return checksum (text) {
        candidates ().map {
            it.copy (
                maxOf (it.first, it.second),
                minOf (it.first, it.second)
            )
        }.first { it.first % it.second == 0 }
    }.sumBy     { it.first / it.second }
}

private fun checksum (text : Sequence<String>, f : List<Int>.() -> Pair<Int, Int>) =
    text.map { it.split ("\\s".toRegex ()).map { it.toInt () }  }
        .map { it.f () }

private fun <T> List<T>.candidates () : List<Pair<T, T>> {
    return (0 until size).flatMap {
        val                 head = get (it)
        drop (it + 1).map { head to it }
    }
}