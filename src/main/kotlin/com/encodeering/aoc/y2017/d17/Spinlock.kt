package com.encodeering.aoc.y2017.d17

import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    println ("spinlock #1: ${spinlock1 (366, 2017)}")
    println ("spinlock #2: ${spinlock2 (366, 50000000)}")
}

fun spinlock1 (steps : Int, iterations : Int) : Int {
    val buffer = LinkedList<Int> ()
        buffer += 0

    spin (steps, iterations) {
                    position,     value ->
        buffer.add (position + 1, value)
    }

    return buffer[buffer.indexOf (iterations) + 1]
}


fun spinlock2 (steps : Int, iterations : Int) : Int {
    val buffer = LinkedList<Int> ()

    spin (steps, iterations) {
            position,                  value ->
        if (position == 0) buffer.add (value)
    }

    return buffer.last
}

private fun spin (steps : Int, iterations : Int, handle : (Int, Int) -> Unit) {
    var position = 0
    var value    = 1

    repeat (iterations) {
        handle (position, value)

        value   += 1
        position = (position + 1 + steps) % (value)
    }
}
