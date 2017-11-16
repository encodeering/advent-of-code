package com.encodeering.aoc.y2016.d3

import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.common.window

/**
 * @author clausen - encodeering@gmail.com
 */
object Day3 {

    @JvmStatic
    fun main(args : Array<String>) {
        println ("horizontally : ${traverse ("/d3/triangles.txt", ::horizontally).size}")
        println ("vertically:    ${traverse ("/d3/triangles.txt", ::vertically).size}")
    }

}

fun horizontally (sequence : Sequence<String>) : List<Triple<Int, Int, Int>> {
    return sequence.map (String::trim)
                   .map { v -> v.split (Regex ("\\s+")) }
                   .map { (a, b, c) -> triangle (a.toInt (), b.toInt (), c.toInt ()) }
                   .filterNotNull ()
                         .toList ()
}

fun vertically (sequence : Sequence<String>) : List<Triple<Int, Int, Int>> {
    return  sequence.map (String::trim)
                    .map { v -> v.split (Regex ("\\s+")) }
                    .map { (a, b, c) -> Triple (a.toInt (), b.toInt (), c.toInt ()) }
                    .toList ()
                    .window (3, 3).flatMap { (t1, t2, t3) ->

        val list = mutableListOf<Triple<Int, Int, Int>> ()

        triangle (t1.first,  t2.first,  t3.first)?.let  (list::add)
        triangle (t1.second, t2.second, t3.second)?.let (list::add)
        triangle (t1.third,  t2.third,  t3.third)?.let  (list::add)

        list
    }
}

fun triangle                       (a : Int, b : Int, c : Int) : Triple<Int, Int, Int>? {
    val          list = sequenceOf (a,       b,       c)

    val window = list.zip (list.drop (1) + list.take (1))
                     .zip (list.drop (2) + list.take (2))

    val invalid = window.map { (sides, opposite) -> sides.first + sides.second to opposite }.any { (sum, opposite) -> sum <= opposite }

    return if (invalid) null else Triple (a, b, c)
}
