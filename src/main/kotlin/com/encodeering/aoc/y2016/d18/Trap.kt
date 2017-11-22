package com.encodeering.aoc.y2016.d18

import com.encodeering.aoc.common.window

/**
 * @author clausen - encodeering@gmail.com
 */
object Day18 {

    @JvmStatic
    fun main (args : Array<String>) {
        println ("safe tiles: ${trapplan (".^.^..^......^^^^^...^^^...^...^....^^.^...^.^^^^....^...^^.^^^...^^^^.^^.^.^^..^.^^^..^^^^^^.^^^..^", 40).count { it == '.' }}")
        println ("safe tiles: ${trapplan (".^.^..^......^^^^^...^^^...^...^....^^.^...^.^^^^....^...^^.^^^...^^^^.^^.^.^^..^.^^^..^^^^^^.^^^..^", 400000).count { it == '.' }}")
    }

}

fun trapplan            (row : CharSequence, length : Int) : CharSequence {
    tailrec fun analyse (row : CharSequence, length : Int, plan : List<CharSequence>) : List<CharSequence> {
        if (length <= 1) return plan

        val next = ".$row.".toList ().window (3).map { w -> w.map (::trap) }.map {
                  (left,     center,     right) ->
                (  left &&   center && ! right) ||
                (! left &&   center &&   right) ||
                (  left && ! center && ! right) ||
                (! left && ! center &&   right)
        }.map {
            when (it) {
                true  -> '^'
                false -> '.'
            }
        }.joinToString ("")

        return analyse (next, length - 1, plan + next)
    }

    return analyse (row, length, listOf (row)).joinToString ("\n")
}

fun trap (char : Char) = char == '^'
