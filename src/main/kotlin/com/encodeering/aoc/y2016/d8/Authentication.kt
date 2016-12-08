package com.encodeering.aoc.y2016.d8

import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day8 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/d8/codes.txt") {
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

class Authentication (val height : Int, val width : Int, val charfy : (Boolean) -> CharSequence = ::charfy) {

    private val m by lazy {
        (0 until   height).map {
            Array (width) { false }
        }
    }

    fun fill (x : Int, y : Int) : Authentication {
        (0 until y).forEach { yv ->
            (0 until x).forEach { xv ->
                m[yv][xv] = true
            }
        }

        return this
    }

    fun rotateCol (x : Int, by : Int) : Authentication {
        val col = m.map { it[x] }
           (col + col).asIterable().drop(height - by % height).take(height).forEachIndexed { y, value ->  m[y][x] = value }

        return this
    }

    fun rotateRow  (y : Int, by : Int) : Authentication {
        val row = m[y]
           (row + row).asIterable ().drop (width - by % width).take (width).forEachIndexed { x, value ->  m[y][x] = value }

        return this
    }

    fun display () : String = m.joinToString ("") { it.joinToString ("", transform = charfy)+ "\n" }

    fun lits () : Int = m.sumBy { it.sumBy { if (it) 1 else 0 } }

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

fun charfy (onoff : Boolean) : CharSequence = if (onoff) "#" else "."