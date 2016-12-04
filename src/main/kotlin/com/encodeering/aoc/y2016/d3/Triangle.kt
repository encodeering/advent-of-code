package com.encodeering.aoc.y2016.d3

import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day3 {

    @JvmStatic
    fun main(args : Array<String>) {
        println ("horizontally : ${traverse ("/d3/triangles.txt", ::horizontally).size}")
    }

}

fun horizontally (sequence : Sequence<String>) : List<Triple<Int, Int, Int>> {
    return sequence.map (String::trim)
                   .map { v -> v.split (Regex ("\\s+")) }
                   .map { (a, b, c) -> triangle (a.toInt (), b.toInt (), c.toInt ()) }
                   .filterNotNull ()
                         .toList ()
}

fun triangle                       (a : Int, b : Int, c : Int) : Triple<Int, Int, Int>? {
    val          list = sequenceOf (a,       b,       c)

    val window = list.zip (list.drop (1) + list.take (1))
                     .zip (list.drop (2) + list.take (2))

    val invalid = window.map { (sides, opposite) -> sides.first + sides.second to opposite }.any { (sum, opposite) -> sum <= opposite }

    return if (invalid) null else Triple (a, b, c)
}