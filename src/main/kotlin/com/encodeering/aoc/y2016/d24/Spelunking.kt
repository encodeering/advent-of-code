package com.encodeering.aoc.y2016.d24

import com.encodeering.aoc.common.Grid
import com.encodeering.aoc.common.Search
import com.encodeering.aoc.common.Sector
import com.encodeering.aoc.common.permutation
import com.encodeering.aoc.common.toGrid
import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.common.window
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
object Day24 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse("/y2016/d24/spelunking.txt") {
            val             spelunking = planify (it.toList ()).also { println (it.display ()) }
            val distances = spelunking.distances { locate { type == SpelunkingSectorType.POI } }.filter { (r, _) -> r.first ().value == "0" }.toList ()

            val oneway = distances.map {
                (_, distance) -> distance
            }.min ()

            val cycle = distances.map {
                                     (r,                                                        distance) ->
                 spelunking.distance (r.last (), spelunking.locate { value == "0" }.first ()) + distance
            }.min ()

            println ("shortest distance: $oneway")
            println ("shortest distance: $cycle")
        }
    }

}

fun planify (stdout : Iterable<CharSequence> ) : Spelunking {
    return   stdout.drop (1).dropLast (1).map { it.drop (1).dropLast (1) }.withIndex ().flatMap {
        (y, value) ->
            value.split ("").filterNot { it.isBlank () }.withIndex ().map {
                (x, c) -> SpelunkingSector (y, x, c)
            }
    }.toGrid ().let (::Spelunking)
}

class Spelunking (val grid : Grid<String>)  {

    val routing = mutableMapOf<Pair<SpelunkingSector, SpelunkingSector>, Int> ()

    fun locate (f : SpelunkingSector.() -> Boolean) = grid.locate { it.f () }.toList ()

    fun display () : String = grid.display {
              sector ->
        when (sector.type) {
                SpelunkingSectorType.Wall   -> "#"
                SpelunkingSectorType.Street -> "."
                SpelunkingSectorType.POI    -> sector.value
            }
        }

    fun distances (way : Spelunking.() -> List<SpelunkingSector>) = way ().permutation ().map {
        it to it.window (2).map {
                     (a, b) ->
            distance (a, b)
        }.sum ()
    }

    fun distance (a : SpelunkingSector, b : SpelunkingSector) : Int {
        return routing.computeIfAbsent (a to b) {
            Route (
                a,
                b,
                Search(
                    storage = { LinkedList() },
                    morph = { it },
                    generate = this::generate
                ).query (a, emptyList ()) { first.ij == b.ij }?.size ?: 0
            ).distance
        }.also {
            routing[b to a] = it
        }
    }

    private fun generate (sector : SpelunkingSector, way : List<Sector<String>>) : Iterable<Pair<SpelunkingSector, List<SpelunkingSector>>> =
        grid.neighbours (sector).filter { it.type != SpelunkingSectorType.Wall }.map { it to way + it }.toList ()

}

data class Route (val start : SpelunkingSector, val end : SpelunkingSector, val distance : Int)

private typealias SpelunkingSector = Sector<String>

private val SpelunkingSector.type get () = when (value) {
    "#"  -> SpelunkingSectorType.Wall
    "."  -> SpelunkingSectorType.Street
    else -> SpelunkingSectorType.POI
}

enum class SpelunkingSectorType { Wall, Street, POI }