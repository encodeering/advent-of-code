package com.encodeering.aoc.y2017.d2

import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d2/checksum.txt") {
        println ("checksum #1: ${checksum (it)}")
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
