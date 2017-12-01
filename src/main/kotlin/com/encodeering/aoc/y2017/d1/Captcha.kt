package com.encodeering.aoc.y2017.d1

import com.encodeering.aoc.common.column
import com.encodeering.aoc.common.matrix
import com.encodeering.aoc.common.rotate
import com.encodeering.aoc.common.toList
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    traverse ("/y2017/d1/captcha.txt") {
        it.forEach {
            println ("captcha #1: ${captcha (it) { 1 }}")
            println ("captcha #2: ${captcha (it) { length / 2 }}")
        }
    }
}

fun captcha      (text : CharSequence,               step : CharSequence.() -> Int) : Int {
    val matrix = (text.toList () + text.rotate (-1 * step (text)).toList ()).matrix (2, text.length)

    return (0 until text.length).map {
        val (a,   b) = matrix.column (it).toList ()

        if  (a == b) a.toString().toInt() else 0
    }.sum ()
}
