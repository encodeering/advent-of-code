package com.encodeering.aoc.y2016.algorithm

/**
 * @author clausen - encodeering@gmail.com
 */
class Code (private val instructions : MutableList<Command>) {

    operator fun get (line : Int) : Command? =
        instructions.getOrElse (line) { null }

    operator fun set (line : Int, value : Command) {
        instructions[line] = value
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
                        code[pos]?.run { pos + apply (state) } ?: return@next
                    )
        }

        next (0)
    }

    fun parse (operations : Sequence<CharSequence>) = Code(operations.map { op ->
        val parameters = op.drop(4).split(" ")

        when {
            op.startsWith("cpy") -> Command.Cpy(parameters[1], parameters[0])
            op.startsWith("inc") -> Command.Inc(parameters[0])
            op.startsWith("dec") -> Command.Dec(parameters[0])
            op.startsWith("jnz") -> Command.Jnz(parameters[0], parameters[1])
            else                 -> throw IllegalStateException("operation $op unknown")
        }
    }.toMutableList())

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
            fun offsetify (num : Int) =
                if        (num != 0) offset.number () ?: state[offset] else null

            val raw = register.number()
            val reg = state[register]

            if (       raw != null) {
                return raw.let(::offsetify) ?: next
            }

            return reg?.let(::offsetify) ?: next
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