package com.encodeering.aoc.y2017.d14

import com.encodeering.aoc.common.Grid
import com.encodeering.aoc.common.matrix
import com.encodeering.aoc.y2017.d10.knothash
import java.math.BigInteger

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    val grid = defrag ("xlqgujun")

    println ("used: ${grid.used ()}")
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

}