package com.encodeering.aoc.y2017.d6

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    val banks = listOf (2, 8, 8, 5, 4, 2, 3, 1, 5, 5, 1, 2, 15, 13, 5, 14)

    println ("steps #1: ${reallocate1 (banks.toIntArray ())}")

    println ("steps #2: ${reallocate2 (banks.toIntArray ())}")
}

fun reallocate1 (banks : IntArray) = reallocate (banks) { it in this }.size - 1

fun reallocate2 (banks : IntArray) = reallocate (banks) { sumBy { item -> if (item == it) 1 else 0 } > 1 }.run {
    val last = last ()

    dropLast (1).run { size - indexOfLast { it == last } }
}

fun reallocate (banks : IntArray, solves : MutableList<Int>.(Int) -> Boolean) : MutableList<Int> {
    tailrec fun perform (banks : IntArray, states : MutableList<Int>) : MutableList<Int> {
        val state = banks.contentHashCode ()
        if (states.solves (state).also { states += state }) return states

        val max = banks.max ()!!
        val idx = banks.indexOf (max)

        val fullcycles    = max / banks.size
        val partialcycles = max % banks.size

        val rightavail  = banks.size - idx - 1
        val rightcycles = if (partialcycles == 0) 0 else if (rightavail > partialcycles) partialcycles else rightavail
        val leftcycles  = if (partialcycles == 0) 0 else                                 partialcycles    - rightcycles

        banks[idx] = 0

        (0       until banks.size).forEach  { banks[it] += fullcycles }

        (0       until leftcycles).forEach  { banks[it] += 1 }

        (idx + 1 until idx + 1 + rightcycles).forEach { banks[it] += 1 }

        return perform (banks, states)
    }

    return perform (banks, mutableListOf<Int> ())
}