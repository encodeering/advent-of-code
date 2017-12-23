package com.encodeering.aoc.y2017.d4

import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    traverse ("/y2017/d4/phrases.txt") {
        println (it.sumBy { if (validate1 (it)) 1 else 0 })
    }

    traverse ("/y2017/d4/phrases.txt") {
        println (it.sumBy { if (validate2 (it)) 1 else 0 })
    }
}

fun String.tokenize () = split ("\\s".toRegex ())

fun validate1 (text : String) = validate (text.tokenize ())

fun validate2 (text : String) = validate (text.tokenize ().map { it.toSortedSet ().joinToString ("") })

private fun validate (phrases : Iterable<String>) : Boolean = phrases.groupBy { it }.all { it.value.size == 1 }