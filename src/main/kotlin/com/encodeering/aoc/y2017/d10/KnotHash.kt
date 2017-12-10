package com.encodeering.aoc.y2017.d10

import com.encodeering.aoc.common.blockwise
import com.encodeering.aoc.common.rotate
import com.encodeering.aoc.common.traverse
import java.lang.Integer.toHexString

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d10/knots.txt") {
        println ("mulitplication:  ${hash1 ((0..255).toList(), it.first().split(',').map { it.toInt () })}")
    }

    traverse ("/y2017/d10/knots.txt") {
        println ("hash:  ${hash2 ((0..255).toList(), it.first())}")
    }
}

fun hash1 (list : List<Int>, knots : List<Int>) = list.hash (knots).run { this[0] * this[1] }

fun hash2 (list : List<Int>, knots : CharSequence) = list.hash (knots.ascii () + listOf (17, 31, 73, 47, 23), 64)
    .blockwise   (16)
        .joinToString ("") { toHexString (it.reduce { a, b -> a xor b }).padStart (2, '0') }

private fun List<Int>.hash                 (knots : List<Int>, runs : Int = 1) : List<Int> {
    tailrec fun combine (input : List<Int>, knots : List<Int>, position : Int, skip : Int) : Knot {
        if                                 (knots.isEmpty ()) return Knot (input, knots, position, skip)

        val      from = position % input.size
        val to = from + knots.first ()

        return combine (
            (input + input).reverse (from, to - 1).subList (from, from + input.size).rotate (from),
            knots.drop (1),
            skip + to,
            skip + 1
        )
    }

    return (0 until runs).fold (Knot (this, knots, 0, 0)) {
                 knot, _ ->
        combine (knot.list, knot.knots, knot.position, knot.skip).copy (knots = knot.knots)
    }.list
}

fun <T> List<T>.reverse (idxA : Int = 0, idxB : Int = size - 1) : List<T> =
    subList (0,    idxA) +
    subList (idxA, idxB + 1).reversed () +
    subList (idxB + 1, size)

private data class Knot (val list : List<Int>, val knots : List<Int>, val position : Int, val skip : Int)

private fun CharSequence.ascii () = map { c -> c.toInt () }.toList()