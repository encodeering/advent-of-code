package com.encodeering.aoc.y2017.d10

import com.encodeering.aoc.common.rotate
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d10/knots.txt") {
        println ("mulitplication:  ${hash1 ((0..255).toList(), it.first().split(',').map { it.toInt () })}")
    }
}

fun hash1 (list : List<Int>, knots : List<Int>) = list.hash (knots).run { this[0] * this[1] }

private fun List<Int>.hash                 (knots : List<Int>) : List<Int> {
    tailrec fun combine (input : List<Int>, knots : List<Int>, position : Int, skip : Int) : List<Int> {
        if                                 (knots.isEmpty ()) return input

        val list =            input + input
        val from = position % input.size

        return combine (
            list.reverse (from, from + knots.first () - 1).subList (from, from + input.size).rotate (from),
            knots.drop (1),
            position + knots.first () + skip,
            skip + 1
        )
    }

    return combine (this, knots, 0, 0)
}

fun <T> List<T>.reverse (idxA : Int = 0, idxB : Int = size - 1) : List<T> =
    subList (0,    idxA) +
    subList (idxA, idxB + 1).reversed () +
    subList (idxB + 1, size)
