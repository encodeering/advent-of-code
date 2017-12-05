package com.encodeering.aoc.y2017.d5

import com.encodeering.aoc.common.number
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d5/twisty.txt") {
        val instructions = it.map { it.number { toInt() }!! }.toMutableList ()

        println ("steps #1: ${twisty1 (instructions)}")
    }

    traverse ("/y2017/d5/twisty.txt") {
        val instructions = it.map { it.number { toInt() }!! }.toMutableList ()

        println ("steps #2: ${twisty2 (instructions)}")
    }
}

fun twisty1 (instructions : MutableList<Int>) = twisty (instructions) {                      1 }

fun twisty2 (instructions : MutableList<Int>) = twisty (instructions) { if (it >= 3) -1 else 1 }

fun twisty (positions : MutableList<Int>, offset : (Int) -> Int) : Int {
    tailrec fun jump (pos : Int, steps : Int) : Int =
        if (pos >= positions.size) steps else jump (pos + positions[pos].also { positions[pos] += offset (it) }, steps + 1)

    return jump (0, 0)
}
