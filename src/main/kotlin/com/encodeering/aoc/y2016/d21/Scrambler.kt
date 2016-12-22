package com.encodeering.aoc.y2016.d21

import com.encodeering.aoc.y2016.io.traverse
import java.lang.Math.abs
import java.lang.Math.floorMod

/**
 * @author clausen - encodeering@gmail.com
 */
object Day21 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/d21/scrambling.txt") {
            println ("password ${scramble ("abcdefgh", it.toList ())}")
        }
    }

}

tailrec fun scramble                   (state : CharSequence, ops : Iterable<CharSequence>) : CharSequence {
    if       (ops.count () == 0) return state

    val cmd = ops.first ()

    return scramble (
        when {
            cmd.startsWith ("swap position") -> {
                val        (a, b) = cmd.findAll (Regex("""(\d+)""")).map (String::toInt).toList ()
                state.swap (a, b)
            }

            cmd.startsWith ("swap letter") -> {
                val        (a, b) = cmd.findAll (Regex ("""letter (\w)""")).toList ()

                state.swap (state.indexOf (a),
                            state.indexOf (b))
            }

            cmd.startsWith ("rotate right") -> {
                val          (by) = cmd.findAll (Regex("""(\d+)""")).map (String::toInt).toList ()
                state.rotate (by)
            }

            cmd.startsWith ("rotate left") -> {
                val           (by) = cmd.findAll (Regex("""(\d+)""")).map (String::toInt).toList ()
                state.rotate (-by)
            }

            cmd.startsWith ("rotate based") -> {
                val rotation = listOf ( 1,  2,  3,  4,  6,  7,  8,  9).withIndex ().map { it.index to it.value }.toMap ()

                val                                  (by) = cmd.findAll (Regex ("""letter (\w)""")).toList ()
                state.rotate (rotation[state.indexOf (by)]!!)
            }

            cmd.startsWith ("move") -> {
                val        (a, b) = cmd.findAll (Regex ("""(\d+)""")).map (String::toInt).toList ()
                state.move (a, b)
            }

            cmd.startsWith ("reverse") -> {
                val           (a, b) = cmd.findAll (Regex ("""(\d+)""")).map (String::toInt).toList ()
                state.reverse (a, b)
            }

            else -> throw IllegalStateException ("cmd $cmd unknown")
        },
        ops.drop (1)
    )
}

fun CharSequence.findAll (regex : Regex) = regex.findAll (this).flatMap { it.groupValues.drop (1).asSequence () }

fun CharSequence.swap (idxA : Int, idxB : Int) : CharSequence = this.mapIndexed { idx, char ->
    when (idx) {
          idxA -> elementAt (idxB)
          idxB -> elementAt (idxA)
          else -> char
    }
}.joinToString ("")

fun CharSequence.move (idxA : Int, idxB : Int) : CharSequence =
    when {
        idxA < idxB -> subSequence (0, idxA).toString () + subSequence (idxA, idxB + 1).rotate (-1).toString () + subSequence (idxB + 1, length)
        else        -> subSequence (0, idxB).toString () + subSequence (idxB, idxA + 1).rotate (+1).toString () + subSequence (idxA + 1, length)
    }

fun CharSequence.reverse (idxA : Int, idxB : Int) : CharSequence =
    subSequence (0,    idxA).toString () +
    subSequence (idxA, idxB + 1).reversed () +
    subSequence (idxB + 1, length)

fun CharSequence.rotate (by : Int) : CharSequence =
    when {
        by == 0 || length - abs (by) == 0 -> this
        by <  0                           -> takeLast (floorMod (length - abs (by), length)).toString () + take (floorMod (         abs (by), length))
        else                              -> takeLast (floorMod (         abs (by), length)).toString () + take (floorMod (length - abs (by), length))
    }