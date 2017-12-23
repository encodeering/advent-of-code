package com.encodeering.aoc.y2017.d14

import com.encodeering.aoc.common.Grid
import com.encodeering.aoc.common.Sector
import com.encodeering.aoc.common.matrix
import com.encodeering.aoc.y2017.d10.knothash
import java.math.BigInteger
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    val grid = defrag ("xlqgujun")

    println ("used: ${grid.used ()}")
    println ("blocks: ${grid.blocks ()}")
}

fun defrag (hash : String) : Defragmentation {
    val matrix = (0..127)
            .map     { "$hash-$it".knothash () }
            .map     { it.map { BigInteger ("$it", 16).toString (2).padStart (4, '0') }.joinToString ("") }
            .flatMap { it.map { it == '1' } }
                .matrix (128, 128)

    return Defragmentation (Grid (matrix))
}

class Defragmentation (private val grid : Grid<Boolean>){

    fun display () = grid.display { if (it.value) "#" else "." }

    fun used ()    = grid.values ().sumBy { if (it) 1 else 0 }

    fun blocks ()  = grid.locate { it.value }.toMutableSet ().let { sectors ->
        val defragmentation = grid.values ().map { if (it) Integer.MIN_VALUE else 0 }.toList ().toTypedArray ()

        var count = 0

        while (sectors.isNotEmpty ()) {
               sectors -= region (sectors.first ()).also {
                   count++

                   it.forEach {       (i,        j, _) ->
                       defragmentation[i * 128 + j] = count
                   }
               }
        }

        count
    }

    private fun region (sector : Sector<Boolean>) : Set<Sector<Boolean>> {
        if (!           sector.value) return emptySet ()

        val seen = mutableSetOf (sector)

        val    candidates = LinkedList<Sector<Boolean>> ().apply { add (sector) }
        while (candidates.isNotEmpty ()) {
            grid.neighbours (candidates.poll ())
                    .filter  { it.value }
                    .forEach {
                        if (it !in seen) {
                                   seen       += it
                                   candidates += it
                        }
                    }
        }

        return seen
    }

}