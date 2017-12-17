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
        println ("dance #2: ${dance2 (moves)}")
    }
}

private fun dance1 (moves : List<String>) = "abcdefghijklmnop".promenade (moves)

private fun dance2 (moves : List<String>) : CharSequence {
    val text : CharSequence = "abcdefghijklmnop"
    val cycles = 1000000000 % repetition (text, moves)

    return (0 until cycles).fold (text) { t, _ -> t.promenade (moves) }
}

private fun repetition (text : CharSequence, moves : List<String>) : Int {
    var sample = text

    val map = LinkedHashMap<CharSequence, Boolean> ()
        map[sample] = true

    while (true) {
            sample = sample.promenade (moves)
        if (sample in map) {
            return map.size -
                   map.entries.withIndex ().find { it.value.key == sample }!!.index
        }

        map[sample] = true
    }
}

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