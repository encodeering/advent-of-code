package com.encodeering.aoc.y2017.d15

import kotlin.coroutines.experimental.buildSequence

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    println ("duel #1: ${duel1 (116, 299)}")
    println ("duel #2: ${duel2 (116, 299)}")
}

fun duel1 (left : Long, right : Long) : Int =
    guard (
        sequence (left,  16807L),
        sequence (right, 48271L),
        40000000
    )

fun duel2 (left : Long, right : Long) : Int =
    guard (
        sequence (left,  16807L) { it % 4 == 0 },
        sequence (right, 48271L) { it % 8 == 0 },
        5000000
    )

fun guard (left : Sequence<Int>, right : Sequence<Int>, steps : Int) =
           left.zip             (right) { l, r -> if (l == r) 1 else 0 }
               .take (steps)
                   .sum ()

private inline fun sequence (start : Long, magic : Long, crossinline f : (Int) -> Boolean = { true }) : Sequence<Int> = buildSequence {
    var value = start

    while (true) {
        value = (value * magic) % Integer.MAX_VALUE
        value.toInt ().let {
            if (f (it)) yield (it and 0xFFFF)
        }
    }
}
