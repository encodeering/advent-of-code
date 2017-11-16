package com.encodeering.aoc.y2016.d11

import com.encodeering.aoc.common.Search
import com.encodeering.aoc.common.cartesian
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
object Day11 {

    @JvmStatic
    fun main(args : Array<String>) {
        /*
        The first floor contains a promethium generator and a promethium-compatible microchip.
        The second floor contains a cobalt generator, a curium generator, a ruthenium generator, and a plutonium generator.
        The third floor contains a cobalt-compatible microchip, a curium-compatible microchip, a ruthenium-compatible microchip, and a plutonium-compatible microchip.
        The fourth floor contains nothing relevant.
        */

        val prg = Code ("PR", Type.Generator)
        val prm = Code ("PR", Type.Microchip)

        val cog = Code ("CO", Type.Generator)
        val com = Code ("CO", Type.Microchip)

        val cug = Code ("CU", Type.Generator)
        val cum = Code ("CU", Type.Microchip)

        val rug = Code ("RU", Type.Generator)
        val rum = Code ("RU", Type.Microchip)

        val plg = Code ("PL", Type.Generator)
        val plm = Code ("PL", Type.Microchip)

        show {
            val laboratory = Laboratory (
                lift   = 0,
                floors = listOf (
                    Floor (0, listOf (prg, prm)),
                    Floor (1, listOf (cog, cug, rug, plg)),
                    Floor (2, listOf (com, cum, rum, plm)),
                    Floor (3)
                )
            )

            route (laboratory) { lift == 3 && floor (3).run {
                prg in items &&
                prm in items &&
                cog in items &&
                com in items &&
                cug in items &&
                cum in items &&
                rug in items &&
                rum in items &&
                plg in items &&
                plm in items
            }}
        }

        val elg = Code ("EL", Type.Generator)
        val elm = Code ("EL", Type.Microchip)

        val dig = Code ("DI", Type.Generator)
        val dim = Code ("DI", Type.Microchip)

        show {
            val laboratory = Laboratory (
                lift   = 0,
                floors = listOf (
                    Floor (0, listOf (prg, prm, elg, elm, dig, dim)),
                    Floor (1, listOf (cog, cug, rug, plg)),
                    Floor (2, listOf (com, cum, rum, plm)),
                    Floor (3)
                )
            )

            route (laboratory) { lift == 3 && floor (3).run {
                elg in items &&
                elm in items &&
                dig in items &&
                dim in items &&
                prg in items &&
                prm in items &&
                cog in items &&
                com in items &&
                cug in items &&
                cum in items &&
                rug in items &&
                rum in items &&
                plg in items &&
                plm in items
            }}
        }
    }

    private fun show (solution : () -> List<Laboratory>) {
        solution ().apply {
            forEachIndexed { idx, laboratory ->
                println(idx + 1)
                println(laboratory.display())
                println()
            }

            println("solution size: $size")
        }
    }

}

fun route (laboratory : Laboratory, solves : Laboratory.() -> Boolean) : List<Laboratory> {
    val search = Search (
        storage  = { LinkedList () },
        morph    = ::morph,
        generate = ::generate
    )

    return search.query (laboratory, listOf(laboratory)) { first.solves () } ?: emptyList()
}

fun morph (laboratory : Laboratory) : String {
    val levelpairs = laboratory.floors.flatMap {
        floor ->
        floor.items.map { code ->
            when                                                          (code.suffix) {
                Type.Generator -> floor.level to laboratory.floors.first { code.copy (suffix = Type.Microchip) in it }.level
                else -> null
            }
        }
    }.filterNotNull ().sortedWith (compareBy ({ it.first }, { it.second }))

    return laboratory.lift.toString () + levelpairs.joinToString ("")
}

fun generate (laboratory : Laboratory, route : List<Laboratory>) : Iterable<Pair<Laboratory, List<Laboratory>>> {
    val                  floor = laboratory.floor ()
    return combinations (floor).flatMap { combo ->
        listOf (
            floor.level + 1,
            floor.level - 1
        ).filter { it >= 0 && it <= laboratory.max }
            .map {                                  level ->
                val targetfloor = laboratory.floor (level)

                val f = floor.copy       (items =       floor.items - combo)
                val t = targetfloor.copy (items = targetfloor.items + combo)

                laboratory.copy (
                    lift   = level,
                    floors = laboratory.floors.map {
                        when (it) {
                            floor       -> f
                            targetfloor -> t
                            else        -> it
                        }
                    }
                )
            }
    }.filterNot (Laboratory::frying).map { it to route + it }
}

data class Laboratory (
    val lift   : Int,
    val floors : List<Floor>
) {

    val max by lazy { floors.sortedBy { it.level }.last ().level }

    fun frying () = floors.any (Floor::frying)

    fun floor (level : Int = lift) : Floor = floors.find { it.level == level }!!

    fun display () : String {
        val           codes = floors.flatMap { it.items }.sortedWith (compareBy ({ it.prefix }, { it.suffix }))
        val padding = codes.maxBy { it.prefix.length }?.prefix?.length ?: 0

        return floors.sortedByDescending { it.level }.joinToString ("\n") {
                   floor ->
            "${if (floor.level == lift) "L " else "  "} F${floor.level} ${codes.joinToString ("".padStart (padding + 1)) { if (it in floor) it.prefix + it.suffix.name[0] else ".".padStart (padding + 1) }}"
        }
    }

}

data class Floor (
    val level : Int,
    val items : List<Code> = emptyList ()
) {

    operator fun contains (code : Code) : Boolean = code in items

    fun frying () = items.filter { it.suffix == Type.Microchip }.any {
        code ->
        code.copy (suffix = Type.Generator) !in this && items.any { it.suffix == Type.Generator }
    }

}

data class Code (val prefix : String, val suffix : Type)

enum class Type { Generator, Microchip }

private fun combinations (floor : Floor) : List<Collection<Code>> {
    val result = mutableListOf<Collection<Code>> ()

    val singles = floor.items
        singles.cartesian (false).mapTo (result) { it.toList ().distinct () }

    return result
}
