package com.encodeering.aoc.y2016.d13

import com.encodeering.aoc.common.Grid
import com.encodeering.aoc.common.Search
import com.encodeering.aoc.common.Sector
import java.lang.Integer.toBinaryString
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
object Day13 {

    @JvmStatic
    fun main (args : Array<String>) {
        val initial = State (1, 1, true)

        val      layout = Layout (50, 50, 1350)
        val                        part1 = layout.path (initial) { (sector, _) -> sector.ij == 39 to 31 }
        println (layout.display (* part1.toTypedArray ()))

        println ("size ${part1.size}")

        val part2 = (0..50).flatMap {
                i ->      (0..50).flatMap {
                j ->  layout.path (initial) { (sector, way) -> sector.ij == i to j || way.size == 50 + 1 }
            }
        }.distinct ()

        println (layout.display (* part2.toTypedArray ()))

        println ("size ${part2.size}")
    }

}

class Layout (height : Int, width : Int, code : Int) {

    /* true := open-space, false := wall */
    private val grid = Grid (height, width) { i, j, _ -> toBinaryString (j*j + 3*j + 2*i*j + i + i*i + code).sumBy { if (it == '1') 1 else 0 } % 2 == 0 }

    fun path (start : State, solves : (Pair<State, List<State>>) -> Boolean) : List<State> {
        val search = Search (
            storage  = { LinkedList () },
            morph    = { it },
            generate = this::generate
        )

        return search.query (start, listOf (start), solves) ?: emptyList ()
    }

    fun display (vararg states : State? = emptyArray ()) = grid.display { if (it in states) "o" else if (it.value) "." else "#" }

    private fun generate       (state : State, paths : List<State>) : Iterable<Pair<State, List<State>>> {
        return grid.neighbours (state)
                .filter { it.value }
                .map    { it to paths + it }
                .toList ()
    }

}

private typealias State = Sector<Boolean>