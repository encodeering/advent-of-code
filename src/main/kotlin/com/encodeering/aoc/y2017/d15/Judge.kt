package com.encodeering.aoc.y2017.d15

import kotlin.coroutines.experimental.buildSequence

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    println ("duel #1: ${duel1 (116, 299)}")
}

fun duel1 (left : Long, right : Long) : Int =
    guard (
        sequence (left,  16807L),
        sequence (right, 48271L),
        40000000
    )

fun guard (left : Sequence<Int>, right : Sequence<Int>, steps : Int) = left.zip (right).take (steps).fold (0) {
    count, (l,              r) ->
    if     (l and 0xFFFF == r and 0xFFFF) count + 1 else count
}

private fun sequence (start : Long, magic : Long) : Sequence<Int> = buildSequence {
    var value = start

    while (true) {
               value = (value * magic) % Integer.MAX_VALUE
        yield (value.toInt ())
    }
}
