package com.encodeering.aoc.y2017.d6

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    val banks = listOf (2, 8, 8, 5, 4, 2, 3, 1, 5, 5, 1, 2, 15, 13, 5, 14)

    println ("steps #1: ${reallocate (banks.toIntArray ())}")
}

fun reallocate (banks : IntArray) : Int {
    tailrec fun perform (banks : IntArray, states : MutableSet<Int>) : Int {
        val state = banks.contentHashCode ()
        if (state in states) return states.size

        states += state

        val max = banks.max ()!!
        val idx = banks.indexOf (max)

        val  fullcycles = max / banks.size

        val (leftcycles, rightcycles) = (max % banks.size).let {
            if (it == 0) return@let 0 to 0

            val rightcycles  = (banks.size - idx - 1).run { if (this > it) it else this }
            val leftcycles   = it - rightcycles

            leftcycles to rightcycles
        }

        banks[idx] = 0

        (0       until banks.size).forEach  { banks[it] += fullcycles }

        (0       until leftcycles).forEach  { banks[it] += 1 }

        (idx + 1 until idx + 1 + rightcycles).forEach { banks[it] += 1 }

        return perform (banks, states)
    }

    return perform (banks, mutableSetOf<Int> ())
}