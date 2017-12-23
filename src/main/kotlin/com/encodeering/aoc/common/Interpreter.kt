package com.encodeering.aoc.common

import kotlin.coroutines.experimental.buildSequence

/**
 * @author clausen - encodeering@gmail.com
 */
class Code (private val instructions : MutableList<Command>, private val optimizer : (MutableList<Command>) -> MutableList<Command> = { it }) {

    var line : Int = 0

    var world : MutableList<Command> = instructions

    val size : Int get () = world.size

    operator fun get (line : Int) : Command? =
        world.getOrElse (line) { null }

    operator fun set (line : Int, value : Command) {
        deoptimize ()
        world[line] = value
    }

    fun optimize () {
        if (world ==           instructions)
            world = optimizer (instructions.toMutableList ())
    }

    fun deoptimize () {
        world = instructions
    }

}

class State (private val defaultval : Int? = null, private val observer : (String, Int) -> Unit = { _, _ -> }) {

    private val registers = mutableMapOf<String, Int> ()

    operator fun get (register : String) = if (defaultval == null) registers[register]
                                           else                    registers.computeIfAbsent (register) { defaultval }

    operator fun set (register : String, value : Int) {
        registers[register] = value

        observer (register,   value)
    }

    fun <T> map (f : State.(String, Int) -> T) = registers.map { (k, v) -> f (k, v) }

    fun convertOrLoad (value : String) = value.number { toInt () } ?: load (value)

    fun load (register : String) = this[register]!!

}

class Interpreter (private val code : Code, private val debug : State.(Command) -> Unit = { }) {

    fun run (state : State) : State {
        tailrec fun next (pos : Int, state : State) : State {
            if           (pos >= code.size) return state

            return next (call (pos, state), state)
        }

        return next (0, state)
    }

    fun lazy (state : State) : Sequence<State> {
        return buildSequence {
            var pos = 0

            while (true) {
                if (pos >= code.size)
                    return@buildSequence

                pos = call (pos, state).also { yield (state) }
            }
        }
    }

    private fun call (pos : Int, state : State) : Int {
                      code.optimize ()
                      code.line = pos
        val command = code[pos]!!

        return code.line + command.apply (code, state).also { debug (state, command) }
    }

}

interface Command {

    fun apply (code : Code, state : State) : Int

}
