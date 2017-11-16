package com.encodeering.aoc.y2016.d6

import com.encodeering.aoc.common.asCharSequence
import com.encodeering.aoc.common.frequence
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day6 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/y2016/d6/transmission.txt") {
            println ("message: ${correct (it) { it.maxBy { it.value }?.key } }")
        }

        traverse ("/y2016/d6/transmission.txt") {
            println ("message: ${correct (it) { it.minBy { it.value }?.key } }")
        }
    }

}

fun correct (codes : Sequence<String>, select : (Map<Char, Int>) -> Char?) : String? {
    return   codes.asIterable()
                  .map (String::asIterable).transpose ()
                  .map { it.asCharSequence ().frequence () }
                  .map (select)
                      .joinToString ("")
}

fun <T> Iterable<Iterable<T>>.transpose () : Iterable<Iterable<T>> {
    return first ().mapIndexed { idx, _ -> map { seq -> seq.elementAt (idx) } }
}
