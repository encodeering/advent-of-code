package com.encodeering.aoc.y2016.d8

import com.encodeering.aoc.common.Grid
import com.encodeering.aoc.common.Matrix.Line.Column
import com.encodeering.aoc.common.Matrix.Line.Row
import com.encodeering.aoc.common.by
import com.encodeering.aoc.common.map
import com.encodeering.aoc.common.rotate
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day8 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d8/codes.txt") {
            val authentication = evaluate (Authentication (6, 50), it)
            println ("lits: ${authentication.lits ()}")
            println (authentication.display ())
        }
    }

}

fun evaluate (authentication : Authentication, sequence : Sequence<String>) = sequence.map { Code.parse (it) }.fold (authentication) { authentication, code ->
    when (code) {
        is Code.Fill -> authentication.fill (code.x, code.y)
        is Code.Rotate ->
           when (code.target) {
               CodeTarget.Column -> authentication.rotateCol (code.id, code.by)
               CodeTarget.Row    -> authentication.rotateRow (code.id, code.by)
               else              -> authentication
           }
    }
}

data class Authentication (private val m : Grid<Boolean>) {

    constructor (height : Int, width : Int) : this (Grid (height, width) { _, _, _ -> false })

    fun fill (x : Int, y : Int)       = Authentication (m.derive { it.map { i, j, state -> if (i in 0 until y && j in 0 until x) true else state } })

    fun rotateCol (x : Int, by : Int) = Authentication (m.derive { it.rotate (x by by, line = Column) })

    fun rotateRow (y : Int, by : Int) = Authentication (m.derive { it.rotate (y by by, line = Row)    })

    fun display () : String = m.display { (_, _, state) -> if (state) "#" else " " }

    fun lits () : Int = m.locate { (_, _, state) -> state }.sumBy { 1 }

}

sealed class Code {

    abstract val target : CodeTarget

    companion object {

        fun parse (line : String) : Code {
            val  (command, args) = line.split (" ", limit = 2)

            return when (command) {
                "rect"   -> args.split ("x").map (String::toInt).let {(x, y) -> Fill (x, y)}
                "rotate" -> {
                    val  (type, id, _, by) = args.split (" ", limit = 4)

                    return when (type) {
                        "row"    -> Rotate (CodeTarget.Row,    id.substringAfter ("=").toInt (), by.toInt ())
                        "column" -> Rotate (CodeTarget.Column, id.substringAfter ("=").toInt (), by.toInt ())
                        else -> throw IllegalStateException ("command $command type $type invalid")
                    }
                }

                else -> throw IllegalStateException ("command $command invalid")
            }
        }

    }

    data class Rotate (override val target : CodeTarget, val id : Int, val by : Int) : Code ()

    data class Fill (val x : Int, val y : Int) : Code () {

        override val target : CodeTarget = CodeTarget.Area

    }

}

enum class CodeTarget { Area, Column, Row }
