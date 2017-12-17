package com.encodeering.aoc.y2017.d17

import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    println ("spinlock #1: ${spinlock1 (366, 2017)}")
}

fun spinlock1 (steps : Int, iterations : Int) : Int {
    val buffer = LinkedList<Int> ()
        buffer += 0

    var position = 0
    var value    = 1

    repeat (iterations) {
        buffer.add (position + 1, value)

        value   += 1
        position = (position + 1 + steps) % (value)
    }

    return buffer[buffer.indexOf (iterations) + 1]
}
