package com.encodeering.aoc.y2016.d1

import com.encodeering.aoc.y2016.d1.Direction.East
import com.encodeering.aoc.y2016.d1.Direction.North
import com.encodeering.aoc.y2016.d1.Direction.South
import com.encodeering.aoc.y2016.d1.Direction.West
import com.encodeering.aoc.y2016.d1.Instruction.F
import com.encodeering.aoc.y2016.d1.Instruction.L
import com.encodeering.aoc.y2016.d1.Instruction.R
import java.lang.Math.abs
import java.lang.Math.floorMod

/**
 * @author Michael Clausen - encodeering@gmail.com
 */
object Day1 {

    @JvmStatic
    fun main(args : Array<String>) {
        val input = "R1, L4, L5, L5, R2, R2, L1, L1, R2, L3, R4, R3, R2, L4, L2, R5, L1, R5, L5, L2, L3, L1, R1, R4, R5, L3, R2, L4, L5, R1, R2, L3, R3, L3, L1, L2, R5, R4, R5, L5, R1, L190, L3, L3, R3, R4, R47, L3, R5, R79, R5, R3, R1, L4, L3, L2, R194, L2, R1, L2, L2, R4, L5, L5, R1, R1, L1, L3, L2, R5, L3, L3, R4, R1, R5, L4, R3, R1, L1, L2, R4, R1, L2, R4, R4, L5, R3, L5, L3, R1, R1, L3, L1, L1, L3, L4, L1, L2, R1, L5, L3, R2, L5, L3, R5, R3, L4, L2, R2, R4, R4, L4, R5, L1, L3, R3, R4, R4, L5, R4, R2, L3, R4, R2, R1, R2, L4, L2, R2, L5, L5, L3, R5, L5, L1, R4, L1, R1, L1, R4, L5, L3, R4, R1, L3, R4, R1, L3, L1, R1, R2, L4, L2, R1, L5, L4, L5"

        val instructions = input.replace (" ", "").split (",").map { Instruction.create (it) }

        var headquarter : Point? = null
        val visited = mutableSetOf<Point> ()

        fun locateHQ (points : List<Point>) : List<Point> {
            if (                                                                                      headquarter == null) {
                points.find { (xp, yp) -> visited.any { (xv, yv) -> xp == xv && yp == yv } }?.apply { headquarter = this }

                visited += points
            }

            return points
        }

        val                          start = Point (0, 0, North)
        val end = instructions.fold (start) { current, instruction -> locateHQ (current.path (instruction)).last () }

        println ("headquarter:    ${distance (start, headquarter!!)}")
        println ("block distance: ${distance (start, end)}")
    }

}

fun distance (a : Point, b : Point) : Int = abs (a.x - b.x) +
                                            abs (a.y - b.y)

data class Point (val x : Int, val y : Int, val direction : Direction) {

    fun move (          instruction : Instruction) : Point {
        val magnitude = instruction.magnitude

        return when (direction.turn (instruction)) {
            North -> Point (x,             y + magnitude, North)
            South -> Point (x,             y - magnitude, South)
            East  -> Point (x + magnitude, y,             East)
            West  -> Point (x - magnitude, y,             West)
        }
    }

    fun path (                      instruction : Instruction) : List<Point> {
        val instructions = (1 until instruction.magnitude).asIterable ().map { F (1) }
        val initial = when (instruction) {
            is L ->         instruction.copy (1)
            is R ->         instruction.copy (1)
            else ->         instruction
        }

        val paths = mutableListOf<Point> ()

        (listOf (initial) + instructions).fold (this) { current, instruction ->
            paths += current.move (instruction)
            paths.last ()
        }

        return paths
    }

}

enum class Direction {

    North, East, South, West;

    fun turn (instruction : Instruction) = values ().let {
        val           next = it.indexOf (this) + instruction.sign
        it [floorMod (next,  it.size)]
    }

}

sealed class Instruction {

    abstract val magnitude : Int
    abstract val sign : Int

    companion object {

        fun create (definition : String) = when (definition.first ()) {
            'L'  -> L (definition.substring (1).toInt ())
            'R'  -> R (definition.substring (1).toInt ())
            'F'  -> F (definition.substring (1).toInt ())
            else -> throw IllegalStateException ("$definition is not a valid instruction")
        }

    }

    data class F (override val magnitude : Int) : Instruction () {

        override val sign : Int = 0

    }

    data class L (override val magnitude : Int) : Instruction () {

        override val sign : Int = -1

    }

    data class R (override val magnitude : Int) : Instruction () {

        override val sign : Int = +1

    }

}