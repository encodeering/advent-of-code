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

fun addone () : Array<Array<Int>>.(Iteration) -> Int {
    var        sequence = 1
    return { ++sequence }
}

fun adjacents () : Array<Array<Int>>.(Iteration) -> Int {
    return { iteration ->

        val position   = iteration.position
        val group      = iteration.group
        val groupid    = iteration.groupid
        val groupidsub = iteration.groupidsub

        when (groupid) {
            0 -> this[position.first - 1][position.second] + this[position.first - 1][position.second - 1]

            1 -> group.let {
                    when (groupidsub) {
                        it.size - 1 -> this[position.first][position.second + 1]                                             + this[position.first - 1][position.second + 1]
                        it.size - 2 -> this[position.first][position.second + 1] + this[position.first - 1][position.second] + this[position.first - 1][position.second + 1]
                        else        -> this[position.first][position.second + 1] + this[position.first - 1][position.second] + this[position.first - 1][position.second + 1] + this[position.first - 1][position.second - 1]
                    }
                }

            2 -> group.let {
                    when (groupidsub) {
                        it.size - 1 -> this[position.first + 1][position.second]                                             + this[position.first + 1][position.second + 1]
                        it.size - 2 -> this[position.first + 1][position.second] + this[position.first][position.second + 1] + this[position.first + 1][position.second + 1]
                        else        -> this[position.first + 1][position.second] + this[position.first][position.second + 1] + this[position.first + 1][position.second + 1] + this[position.first - 1][position.second + 1]
                    }
                }

            3 -> group.let {
                    when (groupidsub) {
                        it.size - 1 -> this[position.first][position.second - 1]                                             + this[position.first + 1][position.second - 1]
                        it.size - 2 -> this[position.first][position.second - 1] + this[position.first + 1][position.second] + this[position.first + 1][position.second - 1]
                        else        -> this[position.first][position.second - 1] + this[position.first + 1][position.second] + this[position.first + 1][position.second - 1] + this[position.first + 1][position.second + 1]
                    }
                }

            4 -> group.let {
                    when (groupidsub) {
                        it.size - 1 -> this[position.first - 1][position.second] + this[position.first][position.second - 1] + this[position.first - 1][position.second - 1]
                        else        -> this[position.first - 1][position.second] + this[position.first][position.second - 1] + this[position.first - 1][position.second - 1] + this[position.first + 1][position.second - 1]
                    }
                }

            else -> throw IllegalStateException ("$groupid")
        }
    }
}

class Spiral (n : Int, initialvalue : Int, supply : Array<Array<Int>>.(Iteration) -> Int) {

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

private fun spiral (n : Int, initialvalue : Int, supply : Array<Array<Int>>.(Iteration) -> Int) : Grid<Int> {
    val edgesize = ceil (sqrt (n.toDouble ())).toInt ().run { this + if (this % 2 == 0) 1 else 0 }

    val spiral = Array (edgesize) {
                 Array (edgesize) { 0 }
    }

    var iterations = 1
    var start = (edgesize / 2).let { it to it }

    spiral[start.first][start.second] = initialvalue

    (0 .. edgesize / 2).forEach {
                               iteration ->
        val     moves = moves (iteration)
        start = moves.flatten ().foldIndexed (start) { progress, previous, move ->
            if (++iterations > n) return@forEach

            val (px, py) = previous
            val ( x,  y) = move

            val position = px + x to py + y

            spiral[position.first][position.second] = spiral.supply (Iteration (position, progress, move, moves))

            position
        }

    }

    return Grid (edgesize, edgesize) { i, j, _ -> spiral[j][i] }
}

private fun moves (iteration : Int) : List<List<Pair<Int, Int>>> {
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
    )
}

data class Iteration (val position : Pair<Int, Int>, private val progress : Int, private val move: Pair<Int, Int>, private val moves : List<List<Pair<Int, Int>>>) {

    val group by lazy {
        moves[groupid]
    }

    val groupid by lazy {
        moves.indexOfFirst { move in it }.run {
            if (this != 0) return@run this

            if (progress == 0) 0 else moves.lastIndex
        }
    }

    val groupidsub by lazy {
        progress - (0 until groupid).sumBy { moves[it].size }
    }

}