package com.encodeering.aoc.y2017.d5

import com.encodeering.aoc.common.primitive.number
import com.encodeering.aoc.common.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    traverse ("/y2017/d5/twisty.txt") {
        println ("steps #1: ${twisty1 (it.instructions ())}")
    }

    traverse ("/y2017/d5/twisty.txt") {
        println ("steps #2: ${twisty2 (it.instructions ())}")
    }
}

private fun Sequence<String>.instructions () = map { it.number { toInt () }!! }.toMutableList ()

fun twisty1 (instructions : MutableList<Int>) = twisty (instructions.toIntArray ()) {                      1 }

fun twisty2 (instructions : MutableList<Int>) = twisty (instructions.toIntArray ()) { if (it >= 3) -1 else 1 }

fun twisty (positions : IntArray, offset : (Int) -> Int) : Int {
    tailrec fun jump (pos : Int,             steps : Int) : Int =
        if           (pos >= positions.size) steps
        else    jump (pos +  positions[pos].also {
                             positions[pos] += offset (it)
                }, steps + 1)

    return jump (0, 0)
}
