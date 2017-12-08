package com.encodeering.aoc.y2017.d8

import com.encodeering.aoc.common.number
import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.y2017.d8.Command.Dec
import com.encodeering.aoc.y2017.d8.Command.Inc
import com.encodeering.aoc.y2017.d8.Guard.Type.Equal
import com.encodeering.aoc.y2017.d8.Guard.Type.Greater
import com.encodeering.aoc.y2017.d8.Guard.Type.GreaterEqual
import com.encodeering.aoc.y2017.d8.Guard.Type.Less
import com.encodeering.aoc.y2017.d8.Guard.Type.LessEqual
import com.encodeering.aoc.y2017.d8.Guard.Type.NotEqual

/**
 * @author clausen - encodeering@gmail.com
 */

fun main(args : Array<String>) {
    traverse ("/y2017/d8/language.txt") {
        println ("max value #1: ${solve1 (it)}")
    }
}

fun solve1 (lines : Sequence<String>) = Interpreter ().run (lines).map { _, value -> value }.max ()!!

class State (private val defaultval : Int = 0) {

    private val registers = mutableMapOf<String, Int> ()

    operator fun get (register : String) = registers.computeIfAbsent (register) { defaultval }

    operator fun set (register : String, value : Int) {
        registers[register] = value
    }

    fun <T> map (f : State.(String, Int) -> T) = registers.map { (k, v) -> f (k, v) }

    fun convertOrLoad (value : String) = value.number { toInt () } ?: load (value)

    fun load (register : String) = this[register]

}

class Interpreter {

    fun run (lines : Sequence<String>, context : State = State ()) : State {
        val commands = parse (lines)

        tailrec fun interpret (pos : Int,                   context : State) : State {
            if                (pos >= commands.size) return context

            commands[pos].apply (context)

            return interpret (pos + 1, context)
        }

        return     interpret (0, context)
    }


    fun parse (lines : Sequence<String>) : List<Command> {
        val splitter = """^(\w+) (\w+) (.+?) \w+ (\w+) (.+?) (.+?)${'$'}""".toRegex ()

        return lines.map {                                                                   op ->
            val (target, command, operand, guard, comparator, value) = splitter.matchEntire (op)!!.destructured

            fun guard () = Guard (guard, Guard.Type.choose (comparator), value)

            when (command) {
                "inc" -> Inc (target, operand, guard ())
                "dec" -> Dec (target, operand, guard ())
                else  -> throw IllegalStateException ("operation $command unknown")
            }
        }.toList ()
    }

}

sealed class Command {

    abstract fun apply (context : State)

    data class Inc (private val register : String, private val operand : String, private val guard : Guard) : Command () {

        override fun apply (context : State) {
            if (guard.applicable (context)) context[register] = context[register] + context.convertOrLoad (operand)
        }

    }

    data class Dec (private val register : String, private val operand : String, private val guard : Guard) : Command () {

        override fun apply (context : State) {
             if (guard.applicable (context)) context[register] = context[register] - context.convertOrLoad (operand)
        }

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