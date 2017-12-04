package com.encodeering.aoc.y2017.d3

import com.encodeering.aoc.common.Grid
import com.encodeering.aoc.common.Search
import com.encodeering.aoc.common.Sector
import java.lang.Math.ceil
import java.lang.Math.sqrt

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    println (Spiral (361527, 1, addone ()).shortest (361527, 1))

    println (Spiral (9 * 9,  1, adjacents ()).display ())
}

fun addone () : Grid<Int>.(Pair<Int, Int>) -> Int {
    var        sequence = 1
    return { ++sequence }
}

fun adjacents () : Grid<Int>.(Pair<Int, Int>) -> Int =
    { (x, y) -> neighbours (y, x, diagonally = true).sumBy { it.value } }

class Spiral (n : Int, initialvalue : Int, supply : Grid<Int>.(Pair<Int, Int>) -> Int) {

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

private fun spiral (n : Int, initialvalue : Int, supply : Grid<Int>.(Pair<Int, Int>) -> Int) : Grid<Int> {
    val edgesize = ceil (sqrt (n.toDouble ())).toInt ().run { this + if (this % 2 == 0) 1 else 0 }

    val spiral = Array (edgesize) {
                 Array (edgesize) { 0 }
    }

    val grid = Grid (edgesize, edgesize) { i, j, _ -> spiral[j][i] }

    var iterations = 1
    var start = (edgesize / 2).let { it to it }

    spiral[start.first][start.second] = initialvalue

    (0 .. edgesize / 2).forEach {
                       iteration ->
        start = moves (iteration).fold (start) { (px, py), ( x,  y) ->
            if (++iterations > n) return@forEach

            (px + x to py + y).also { spiral[it.first][it.second] = grid.supply (it) }
        }
    }

    return grid
}

private fun moves (iteration : Int) : List<Pair<Int, Int>> {
    //  #0  1 1 2 2 2
    //  #1  1 3 4 4 4
    //  #2  1 5 6 6 6

    val first  = 1
    val second = 2 *  iteration + 1
    val rest   = 2 * (iteration + 1)

    return listOf (
        (0 until first).map  {  1 to  0 },
        (0 until second).map {  0 to -1 },
        (0 until rest).map   { -1 to  0 },
        (0 until rest).map   {  0 to +1 },
        (0 until rest).map   {  1 to  0 }
    ).flatten ()
}
