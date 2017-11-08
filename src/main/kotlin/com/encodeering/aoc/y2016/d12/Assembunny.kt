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


class State {

    private val registers : MutableMap<String, Int> = mutableMapOf ()

    operator fun get (register : String) : Int? =
        registers[register]

    operator fun set (register : String, value : Int) {
        registers[register] = value
    }

}

class Interpreter {

    fun run                      (operations : Sequence<CharSequence>, state : State) {
        val instructions = parse (operations).toList ()

        tailrec fun next                                  (pos : Int) {
                    next (
                        instructions.elementAtOrNull (pos)?.run { pos + apply (state) } ?: return@next
                    )
        }

        next (0)
    }

    fun parse (operations : Sequence<CharSequence>) = operations.map { op ->
        val parameters = op.drop (4).split (" ")

        when {
            op.startsWith ("cpy") -> Command.Cpy (parameters[1], parameters[0])
            op.startsWith ("inc") -> Command.Inc (parameters[0])
            op.startsWith ("dec") -> Command.Dec (parameters[0])
            op.startsWith ("jnz") -> Command.Jnz (parameters[0], parameters[1])
            else -> throw IllegalStateException("operation $op unknown")
        }
    }

}

sealed class Command {

    val next = 1

    abstract fun apply (state : State) : Int

    data class Cpy (val register : String, val supply : String) : Command () {

        override fun apply (state : State) : Int {
            state[register] = supply.number () ?: state[supply]!!
            return next
        }

    }

    data class Inc (val register : String) : Command () {

        override fun apply   (state : State) : Int {
            state[register] = state[register]!! + 1
            return next
        }

    }

    data class Dec (val register : String) : Command () {

        override fun apply   (state : State) : Int {
            state[register] = state[register]!! - 1
            return next
        }

    }

    data class Jnz (val register : String, val offset : String) : Command () {

        override fun apply (state : State) : Int {
            fun offsetify (check : Int) = if (check != 0) offset.number () else null

            var goto = register.number ()?.let (::offsetify)
                goto = goto ?: state[register]?.let (::offsetify)

            return goto ?: next
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
