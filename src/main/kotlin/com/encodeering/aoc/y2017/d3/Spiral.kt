package com.encodeering.aoc.y2017.d3

import com.encodeering.aoc.common.Grid
import com.encodeering.aoc.common.Search
import com.encodeering.aoc.common.Sector
import java.lang.Math.ceil
import java.lang.Math.sqrt
import kotlin.coroutines.experimental.buildSequence

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    println (Spiral (361527, 1, addone ()).shortest (361527, 1))

    println (Spiral (9 * 9,  1, adjacents ()).display ())

    // https://oeis.org/A141481
}

fun addone () : Grid<Int>.(Coordinate) -> Int {
    var        sequence = 1
    return { ++sequence }
}

fun adjacents () : Grid<Int>.(Coordinate) -> Int =
    { (i, j) -> neighbours (i, j, diagonally = true).sumBy { it.value } }

class Spiral (n : Int, initialvalue : Int, supply : Grid<Int>.(Coordinate) -> Int) {

    val grid = spiral (n, initialvalue, supply)

    fun display () = grid.display (8)

    fun shortest (start : Int, target : Int) : Int {
        val s = grid.locate { it.value == start  }.first ()
        val e = grid.locate { it.value == target }.first ()

        return Search.bfs (generate = this::generate).query (s, emptyList ()) { first == e }!!.size
    }

    private fun generate (sector : Sector<Int>, path : List<Sector<Int>>) =
        grid.neighbours  (sector).map { it to path + it }.toList ()

}

private fun spiral (n : Int, initialvalue : Int, supply : Grid<Int>.(Coordinate) -> Int) : Grid<Int> {
    val edgesize = ceil (sqrt (n.toDouble ())).toInt ().run { this + if (this % 2 == 0) 1 else 0 }

    val spiral = Array (edgesize) {
                 Array (edgesize) { 0 }
    }

    val grid = Grid (edgesize, edgesize) { i, j, _ -> spiral[i][j] }

    var iterations = 1
    var start = (edgesize / 2).let { it to it }

    spiral[start.first][start.second] = initialvalue

    (0 .. edgesize / 2).forEach {
                       iteration ->
        start = moves (iteration).fold (start) { (pi, pj), (i, j) ->
            if (++iterations > n) return@forEach

            (pi + i to pj + j).also { spiral[it.first][it.second] = grid.supply (it) }
        }
    }

    return grid
}

private fun moves (iteration : Int) : Sequence<Coordinate> {
    //  #0  1 1 2 2 2
    //  #1  1 3 4 4 4
    //  #2  1 5 6 6 6

    val first  = 1
    val second = 2 *  iteration + 1
    val rest   = 2 * (iteration + 1)

    return buildSequence {              // i to  j
        (0 until first).forEach  { yield ( 0 to  1) }
        (0 until second).forEach { yield (-1 to  0) }
        (0 until rest).forEach   { yield ( 0 to -1) }
        (0 until rest).forEach   { yield (+1 to  0) }
        (0 until rest).forEach   { yield ( 0 to  1) }
    }
}

private typealias Coordinate = Pair<Int, Int>