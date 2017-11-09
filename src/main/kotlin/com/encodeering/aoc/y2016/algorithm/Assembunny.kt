package com.encodeering.aoc.y2016.algorithm

import com.encodeering.aoc.y2016.extension.window

/**
 * @author clausen - encodeering@gmail.com
 */

operator fun <T> List<T>.component6 () : T = get (5)

class Code (private val instructions : MutableList<Command>) {

    var world : MutableList<Command> = instructions

    operator fun get (line : Int) : Command? =
        world.getOrElse (line) { null }

    operator fun set (line : Int, value : Command) {
        deoptimize ()
        world[line] = value
    }

    fun optimize () {
        if (world != instructions) return

        world = instructions.toMutableList ()

        // cpy b c
        // inc a
        // dec c
        // jnz c -2
        // dec d
        // jnz d -5
        world.window (6).withIndex ().map {
            val (cpy, inc, dec1, jmp1, dec2, jmp2) = it.value

            if (cpy  !is Command.Cpy) return@map null
            if (inc  !is Command.Inc) return@map null
            if (dec1 !is Command.Dec) return@map null
            if (jmp1 !is Command.Jnz) return@map null
            if (dec2 !is Command.Dec) return@map null
            if (jmp2 !is Command.Jnz) return@map null

            val aux = "${inc.register}~"

            if (dec1.register == cpy.register &&
                dec1.register == jmp1.register &&
                dec2.register == jmp2.register &&
                jmp1.offset   == "-2" &&
                jmp2.offset   == "-5")
                    IndexedValue (it.index, listOf (
                        cpy,
                        Command.Mpy (aux, dec1.register, dec2.register),
                        Command.Inc (inc.register, aux),
                        Command.Cpy (aux, "0"),
                        Command.Cpy (dec1.register, "0"),
                        Command.Cpy (dec2.register, "0")
                    ))

            else null
        }.filterNotNull ().forEach { (line, commands) ->
            (0..5).forEach {
                world.removeAt (line)
            }

            commands.withIndex ().forEach {
                                       (pos,   cmd) ->
                world.add (line + pos, cmd)
            }
        }
    }

    fun deoptimize () {
        world = instructions
    }

}

class State {

    private val registers : MutableMap<String, Int> = mutableMapOf ()

    operator fun get (register : String) : Int? =
        registers[register]

    operator fun set (register : String, value : Int) {
        registers[register] = value
    }

}

class Interpreter {

    fun run              (operations : Sequence<CharSequence>, state : State) {
        val code = parse (operations)

        tailrec fun next                     (pos : Int) {
                    next (
                        run {
                            code.optimize ()
                            code[pos]?.run { pos + apply (state) }
                        } ?: return@next
                    )
        }

        next (0)
    }

    fun parse (operations : Sequence<CharSequence>) = Code(operations.map { op ->
        val parameters = op.drop(4).split(" ")

        when {
            op.startsWith("cpy") -> Command.Cpy(parameters[1], parameters[0])
            op.startsWith("inc") -> Command.Inc(parameters[0], parameters.getOrElse(1) { "1" })
            op.startsWith("dec") -> Command.Dec(parameters[0], parameters.getOrElse(1) { "1" })
            op.startsWith("jnz") -> Command.Jnz(parameters[0], parameters[1])
            op.startsWith("mpy") -> Command.Mpy(parameters[2], parameters[0], parameters[1])
            else                 -> throw IllegalStateException("operation $op unknown")
        }
    }.toMutableList())

}

sealed class Command {

    val next = 1

    abstract fun apply (state : State) : Int
    protected fun State.convertOrLoad (value : String) = value.number () ?: load(value)!!

    protected fun State.load (value : String) = this[value]

    data class Cpy (val register : String, val supply : String) : Command () {

        override fun apply (state : State) : Int {
            state[register] = state.convertOrLoad (supply)
            return next
        }

    }

    data class Inc (val register : String, val value : String) : Command () {

        override fun apply   (state : State) : Int {
            state[register] = state.load (register)!! + state.convertOrLoad (value)
            return next
        }

    }

    data class Dec (val register : String, val value : String) : Command () {

        override fun apply   (state : State) : Int {
            state[register] = state.load (register)!! - state.convertOrLoad (value)
            return next
        }

    }

    data class Jnz (val register : String, val offset : String) : Command () {

        override fun apply (state : State) : Int {
            fun offsetify (num : Int) =
                if        (num != 0) state.convertOrLoad (offset) else null

            val raw = register.number()
            val reg = state.load(register)

            if (       raw != null) {
                return raw.let(::offsetify) ?: next
            }

            return reg?.let(::offsetify) ?: next
        }

    }

    data class Mpy (val register : String, val first : String, val second : String) : Command () {

        override fun apply (state : State) : Int {
            state[register] = state.convertOrLoad (first) * state.convertOrLoad (second)
            return next
        }

    }

}

private fun String.number () : Int? {
    return try {
        this.toInt ()
    } catch (e : NumberFormatException) {
        null
    }
}