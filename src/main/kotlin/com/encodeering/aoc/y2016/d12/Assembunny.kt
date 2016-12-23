package com.encodeering.aoc.y2016.d12

import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day12 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/d12/instructions.txt") {
            val interpreter = Interpreter ()
                interpreter.run (it)

            println ("register a: ${interpreter["a"]}")
        }

        traverse ("/d12/instructions.txt") {
            val interpreter = Interpreter ()
                interpreter["c"] = 1
                interpreter.run (it)

            println ("register a: ${interpreter["a"]}")
        }
    }

}


fun Interpreter.run          (operations : Sequence<CharSequence>) {
    val instructions = parse (operations).toList ()

    val interpreter = this

    tailrec fun next                             (pos : Int) {
                next (
                    instructions.elementAtOrNull (pos).run {
                        if   (this == null) return@next

                        when (this) {
                            is Command.Cpy -> { interpreter[register] = supply ();                   pos + 1 }
                            is Command.Inc -> { interpreter[register] = interpreter[register]!! + 1; pos + 1 }
                            is Command.Dec -> { interpreter[register] = interpreter[register]!! - 1; pos + 1 }
                            is Command.Jnz -> {                                                      pos + if (check ()) offset else 1 }
                            else           ->                                                        pos + 1
                        }
                    }
                )
    }

    next (0)
}


class Interpreter {

    val registers : MutableMap<String, Int> = mutableMapOf ()

    fun parse (operations : Sequence<CharSequence>) = operations.map { op ->
        val parameters = op.drop (4).split (" ")

        when {
            op.startsWith ("cpy") -> {
                val (supply, register) = parameters

                supply.number ()?.run { Command.Cpy (register) { this }                     }
                                ?:      Command.Cpy (register) { this@Interpreter[supply]!! }
            }
            op.startsWith ("inc") -> Command.Inc (parameters[0])
            op.startsWith ("dec") -> Command.Dec (parameters[0])
            op.startsWith ("jnz") -> {
                val (supply, offset) = parameters

                offset.number ()?.run {
                    Command.Jnz (this) {
                        supply.number ()?.run { this != 0 } ?: this@Interpreter[supply] != null && this@Interpreter[supply] != 0
                    }
                }
            }
            else -> throw IllegalStateException("operation $op unknown")
        }
    }

    operator fun get (register : String) : Int? =
        registers[register]

    operator fun set (register : String, value : Int) {
        registers[register] = value
    }

}

sealed class Command {

    data class Cpy (val register : String, val supply : () -> Int) : Command ()

    data class Inc (val register : String) : Command ()

    data class Dec (val register : String) : Command ()

    data class Jnz (val offset : Int, val check : () -> Boolean) : Command ()

}

private fun String.number () : Int? {
    return try {
        this.toInt ()
    } catch (e : NumberFormatException) {
        null
    }
}
