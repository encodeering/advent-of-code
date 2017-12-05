package com.encodeering.aoc.y2017.d5

import com.encodeering.aoc.common.number
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d5/twisty.txt") {
        val instructions = it.map { it.number { toInt() }!! }.toMutableList ()

        println ("steps #1: ${twisty (instructions)}")
    }
}

fun twisty (positions : MutableList<Int>) : Int {
    tailrec fun jump (pos : Int, steps : Int) : Int =
        if (pos >= positions.size) steps else jump (pos + positions[pos].also { positions[pos]++ }, steps + 1)

    return jump (0, 0)
}
