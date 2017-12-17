package com.encodeering.aoc.y2017.d16

import com.encodeering.aoc.common.rotate
import com.encodeering.aoc.common.swap
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    traverse ("/y2017/d16/promenade.txt") {
        val moves = it.first ().split(',')

        println ("dance #1: ${dance1 (moves)}")
    }
}

private fun dance1 (moves : List<String>) = "abcdefghijklmnop".promenade (moves)

fun CharSequence.promenade (moves : List<String>): CharSequence {
    return moves.fold (this) { p, move ->
        val  (cmd, parameter) = """(\w)(.*)""".toRegex ().matchEntire (move)!!.destructured

        when (cmd) {
            "s"  -> p.rotate (parameter.toInt ())
            "p"  -> parameter.split ('/').let { (l, r) -> p.swap (p.indexOf (l), p.indexOf (r)) }
            "x"  -> parameter.split ('/').let { (l, r) -> p.swap (l.toInt ()   , r.toInt  ())   }
            else -> throw IllegalStateException ("command $cmd unknown")
        }
    }
}