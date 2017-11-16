package com.encodeering.aoc.y2016.d13

import com.encodeering.aoc.common.Search
import java.lang.Integer.toBinaryString
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
object Day13 {

    @JvmStatic
    fun main(args : Array<String>) {
        val      layout = Layout (50, 50, 1350)

        val                        part1 = layout.path (1 to 1) { (state, _) -> state.position == 31 to 39 }.map { it.position }
        println (layout.display (* part1.toTypedArray ()))

        println ("size ${part1.size}")

        val part2 = (0..50).flatMap {
                x ->      (0..50).flatMap {
                y ->  layout.path (1 to 1) { (state, way) -> state.position == x to y || way.size == 50 + 1 }
            }
        }.map { it.position }.distinct ()

        println (layout.display (* part2.toTypedArray ()))

        println ("size ${part2.size}")
    }

}

class Layout (val width : Int, val height : Int, code : Int) {

    val layout by lazy {
        /* true := open-space, false := wall */
        fun materialize (x : Int, y : Int) : Boolean = toBinaryString (x*x + 3*x + 2*x*y + y + y*y + code).sumBy { if (it == '1') 1 else 0 } % 2 == 0

        Array    (height) { y ->
            Array (width) { x -> materialize (x, y) }
        }
    }

    fun path                (start : Pair<Int, Int>, solves : (Pair<State, List<State>>) -> Boolean) : List<State> {
        val initial = State (start)

        val search = Search (
            storage  = { LinkedList () },
            morph    = { it },
            generate = this::generate
        )

        return search.query (initial, listOf (initial), solves) ?: emptyList ()
    }

    fun display (vararg positions : Pair<Int, Int>? = emptyArray ()) = layout.withIndex ().joinToString ("\n") {
        (y, row) -> row.withIndex ().joinToString ("") {
            (x, state) ->
                if (x to y in positions) "o" else if (state) "." else "#"
        }
    }

    private fun generate (state : State, paths : List<State>) : Iterable<Pair<State, List<State>>> {
        return sequenceOf (
            state.x - 1 to state.y,
            state.x + 1 to state.y,
            state.x     to state.y + 1,
            state.x     to state.y - 1
        ).filter {
            (x, y) -> (x in 0 .. (width  - 1)) &&
                      (y in 0 .. (height - 1))
        }.filter {
            (x, y) -> layout[y][x]
        }.map {
            State (it).run { this to paths + this }
        }.toList ()
    }

}

data class State (val position : Pair<Int, Int>) {

    val x : Int get () = position.first
    val y : Int get () = position.second

}