package com.encodeering.aoc.y2016.d24

import com.encodeering.aoc.common.Search
import com.encodeering.aoc.common.permutation
import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.common.window
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
object Day24 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse("/d24/spelunking.txt") {
            val grid = Grid (planify (it.toList ())).also { println (it.display ()) }
            val distances = grid.distances { locate { type == SectorType.POI } }.filter { (r, _) -> r.first ().description == "0" }.toList ()

            val oneway = distances.map {
                (_, distance) -> distance
            }.min ()

            val cycle = distances.map {
                               (r,                                                        distance) ->
                 grid.distance (r.last (), grid.locate { description == "0" }.first ()) + distance
            }.min ()

            println ("shortest distance: $oneway")
            println ("shortest distance: $cycle")
        }
    }

}

fun planify (stdout : Iterable<CharSequence> ) : Iterable<Sector> {
    return   stdout.drop (1).dropLast (1).map { it.drop (1).dropLast (1) }.withIndex ().flatMap {
        (y, value) ->
            value.split ("").filterNot { it.isBlank () }.withIndex ().map {
                (x, c) -> Sector (x, y, c)
            }
    }
}

class Grid (val sectors : Iterable<Sector>)  {

    val xy by lazy { sectors.toXY () }

    val routing = mutableMapOf<Pair<Sector, Sector>, Int> ()

    operator fun get (position : Pair<Int, Int>) = xy[position.second]?.find { it.position.first == position.first }

    fun locate (f : Sector.() -> Boolean) = sectors.filter { it.f () }

    fun display () : String {
        return xy.values.run {
            joinToString ("\n") {
                it.joinToString ("") { sector ->
                    when (sector.type) {
                        SectorType.Wall   -> "#"
                        SectorType.Street -> "."
                        SectorType.POI    -> sector.description
                    }
                }
            }
        }
    }

    fun distances (way : Grid.() -> List<Sector>) = way ().permutation ().map {
        it to it.window (2).map {
                     (a, b) ->
            distance (a, b)
        }.sum ()
    }

    fun distance (a : Sector, b : Sector) : Int {
        return routing.computeIfAbsent (a to b) {
            Route (
                a,
                b,
                Search(
                    storage = { LinkedList() },
                    morph = { it },
                    generate = this::generate
                ).query (a, emptyList ()) { first.position == b.position }?.size ?: 0
            ).distance
        }.also {
            routing[b to a] = it
        }
    }

    private fun generate (sector : Sector, way : List<Sector>) : Iterable<Pair<Sector, List<Sector>>> =
        neighbours (sector, viable = true).map { it to way + it }.toList ()

    private fun neighbours (sector : Sector, viable : Boolean) = sector.let { (x, y) ->
        sequenceOf (
            this[x + 1 to y],
            this[x - 1 to y],
            this[x     to y + 1],
            this[x     to y - 1]
        ).filterNotNull ().filter {
            when {
                viable -> it.type != SectorType.Wall
                else   -> true
            }
        }.toList ()
    }

}

fun Iterable<Sector>.toXY () = sortedWith (
    compareBy (
        { it.position.second },
        { it.position.first  }
    )
).groupBy { it.position.second }

data class Route (val start : Sector, val end : Sector, val distance : Int)

data class Sector (val x: Int, val y: Int, val description: String) {

    val type get () = when (description) {
        "#"  -> SectorType.Wall
        "."  -> SectorType.Street
        else -> SectorType.POI
    }

    val position by lazy { x.toInt () to y.toInt () }

}

enum class SectorType { Wall, Street, POI }