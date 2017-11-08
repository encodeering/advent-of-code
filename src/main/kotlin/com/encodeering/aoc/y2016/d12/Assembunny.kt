package com.encodeering.aoc.y2016.d12

import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day12 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/d12/instructions.txt") {
            val state = State ()

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }

        traverse ("/d12/instructions.txt") {
            val state = State ()
                state["c"] = 1

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }
    }

}


fun Interpreter.run          (operations : Sequence<CharSequence>, state : State) {
    val instructions = parse (operations).toList ()

    tailrec fun next                             (pos : Int) {
                next (
                    instructions.elementAtOrNull (pos).run {
                        if   (this == null) return@next

                        when (this) {
                            is Command.Cpy -> { state[register] = supply (state);        pos + 1 }
                            is Command.Inc -> { state[register] = state[register]!! + 1; pos + 1 }
                            is Command.Dec -> { state[register] = state[register]!! - 1; pos + 1 }
                            is Command.Jnz -> {                                          pos + if (check (state)) offset else 1 }
                            else           ->                                            pos + 1
                        }
                    }
                )
    }

    next (0)
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

    fun parse (operations : Sequence<CharSequence>) = operations.map { op ->
        val parameters = op.drop (4).split (" ")

        when {
            op.startsWith ("cpy") -> {
                val (supply, register) = parameters

                supply.number ()?.run { Command.Cpy (register) { this@run }     }
                                ?:      Command.Cpy (register) { this[supply]!! }
            }
            op.startsWith ("inc") -> Command.Inc (parameters[0])
            op.startsWith ("dec") -> Command.Dec (parameters[0])
            op.startsWith ("jnz") -> {
                val (supply, offset) = parameters

                offset.number ()?.run {
                    Command.Jnz (this) {
                        supply.number ()?.run { this != 0 }
                                        ?:run { this[supply] != null && this[supply] != 0 }
                    }
                }
            }
            else -> throw IllegalStateException("operation $op unknown")
        }
    }

}

sealed class Command {

    data class Cpy (val register : String, val supply : State.() -> Int) : Command ()

    data class Inc (val register : String) : Command ()

    data class Dec (val register : String) : Command ()

    data class Jnz (val offset : Int, val check : State.() -> Boolean) : Command ()

}

private fun String.number () : Int? {
    return try {
        this.toInt ()
    } catch (e : NumberFormatException) {
        null
    }
}
