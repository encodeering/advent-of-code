package com.encodeering.aoc.y2017.d8

import com.encodeering.aoc.common.language.Code
import com.encodeering.aoc.common.language.Command
import com.encodeering.aoc.common.language.Interpreter
import com.encodeering.aoc.common.language.State
import com.encodeering.aoc.common.io.traverse
import com.encodeering.aoc.y2017.d8.Guard.Type.Equal
import com.encodeering.aoc.y2017.d8.Guard.Type.Greater
import com.encodeering.aoc.y2017.d8.Guard.Type.GreaterEqual
import com.encodeering.aoc.y2017.d8.Guard.Type.Less
import com.encodeering.aoc.y2017.d8.Guard.Type.LessEqual
import com.encodeering.aoc.y2017.d8.Guard.Type.NotEqual

/**
 * @author clausen - encodeering@gmail.com
 */

fun main (args : Array<String>) {
    traverse ("/y2017/d8/language.txt") {
        val code = Language.parse (it)

        println ("max value #1: ${solve1 (code)}")
        println ("max value #2: ${solve2 (code)}")
    }
}

fun solve1 (code : Code) = Language.run (code).map { _, value -> value }.max ()!!

fun solve2 (code : Code) : Int {
    var                                              maximum = Integer.MIN_VALUE
    val state = State (defaultval = 0) { _, value -> maximum = maxOf (maximum, value) }

    Language.run (code, state)

    return maximum
}

object Language {

    fun run (code : Code, context : State = State (defaultval = 0)) = Interpreter (code).run (context)

    fun parse (lines : Sequence<String>) = Code (lines.map { op ->
        val splitter = """^(\w+) (\w+) (.+?) \w+ (\w+) (.+?) (.+?)${'$'}""".toRegex ()

        val (target, command, operand, guard, comparator, value) = splitter.matchEntire (op)!!.destructured

        fun guard () = Guard (guard, Guard.Type.choose (comparator), value)

        when (command) {
            "inc" -> Inc (target, operand, guard ())
            "dec" -> Dec (target, operand, guard ())
            else  -> throw IllegalStateException ("operation $command unknown")
        }
    }.toMutableList ())

}

data class Inc (private val register : String, private val operand : String, private val guard : Guard) : Command {

    override fun apply (code : Code, state : State) : Int {
        if (guard.applicable (state)) state[register] = state.load (register) + state.convertOrLoad (operand)

        return 1
    }

}

data class Dec (private val register : String, private val operand : String, private val guard : Guard) : Command {

    override fun apply (code : Code, state : State) : Int {
        if (guard.applicable (state)) state[register] = state.load (register) - state.convertOrLoad (operand)

        return 1
    }

}

data class Guard (val register : String, val comparator : Guard.Type, val value : String) {

    enum class Type {
        Less, LessEqual, Equal, GreaterEqual, Greater,
                      NotEqual;

        companion object {

            fun choose (code : String) = when (code) {
                "<"  -> Less
                "<=" -> LessEqual
                "==" -> Equal
                ">=" -> GreaterEqual
                ">"  -> Greater
                "!=" -> NotEqual
                else -> throw IllegalStateException ("code $code unknown")
            }

        }
    }

    fun applicable (context : State) : Boolean {
        val left  = context.convertOrLoad (register)
        val right = context.convertOrLoad (value)

        return when (comparator) {
            Less         -> left <  right
            LessEqual    -> left <= right
            Equal        -> left == right
            GreaterEqual -> left >= right
            Greater      -> left >  right
            NotEqual     -> left != right
        }
    }

}