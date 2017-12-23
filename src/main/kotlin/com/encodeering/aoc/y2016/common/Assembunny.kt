package com.encodeering.aoc.y2016.common

import com.encodeering.aoc.common.Code
import com.encodeering.aoc.common.Command
import com.encodeering.aoc.common.Interpreter
import com.encodeering.aoc.common.State
import com.encodeering.aoc.common.number
import com.encodeering.aoc.common.window

/**
 * @author clausen - encodeering@gmail.com
 */

operator fun <T> List<T>.component6 () : T = get (5)

private fun optimize (instructions : MutableList<Command>) : MutableList<Command> {
    // cpy b c
    // inc a
    // dec c
    // jnz c -2
    // dec d
    // jnz d -5
    instructions.window (6).withIndex ().map {
        val (cpy, inc, dec1, jmp1, dec2, jmp2) = it.value

        if (cpy  !is Cpy) return@map null
        if (inc  !is Inc) return@map null
        if (dec1 !is Dec) return@map null
        if (jmp1 !is Jnz) return@map null
        if (dec2 !is Dec) return@map null
        if (jmp2 !is Jnz) return@map null

        val aux = "${inc.register}~"

        if (dec1.register == cpy.register &&
            dec1.register == jmp1.register &&
            dec2.register == jmp2.register &&
            jmp1.offset   == "-2" &&
            jmp2.offset   == "-5")
                IndexedValue (it.index, listOf (
                    cpy,
                    Mpy (aux, dec1.register, dec2.register),
                    Inc (inc.register, aux),
                    Cpy (aux, "0"),
                    Cpy (dec1.register, "0"),
                    Cpy (dec2.register, "0")
                ))

        else null
    }.filterNotNull ().forEach { (line, commands) ->
        (0..5).forEach {
            instructions.removeAt (line)
        }

        commands.withIndex ().forEach {
                                    (pos, cmd) ->
            instructions.add (line + pos, cmd)
        }
    }

    return instructions
}

object Assembunny {

    fun run (code : Code, state : State = State ()) = Interpreter (code).run (state)

    fun lazy (code : Code, state : State = State (), debug : State.(Command) -> Unit) = Interpreter (code, debug).lazy (state)

    fun parse (operations : Sequence<CharSequence>) = Code (operations.map { op ->
        val parameters = op.drop (4).split (" ")

        when {
            op.startsWith ("cpy") -> Cpy (parameters[1], parameters[0])
            op.startsWith ("inc") -> Inc (parameters[0], parameters.getOrElse (1) { "1" })
            op.startsWith ("dec") -> Dec (parameters[0], parameters.getOrElse (1) { "1" })
            op.startsWith ("jnz") -> Jnz (parameters[0], parameters[1])
            op.startsWith ("mpy") -> Mpy (parameters[2], parameters[0], parameters[1])
            op.startsWith ("tgl") -> Tgl (parameters[0])
            op.startsWith ("out") -> Out (parameters[0])
            else                 -> throw IllegalStateException ("operation $op unknown")
        }
    }.toMutableList (), optimizer = ::optimize)

}

data class Cpy (val register : String, val supply : String) : Command {

    override fun apply (code : Code, state : State) : Int {
        state[register] = state.convertOrLoad (supply)
        return 1
    }

}

data class Inc (val register : String, val value : String) : Command {

    override fun apply (code : Code, state : State) : Int {
        state[register] = state.load (register) + state.convertOrLoad (value)
        return 1
    }

}

data class Dec (val register : String, val value : String) : Command {

    override fun apply (code : Code, state : State) : Int {
        state[register] = state.load (register) - state.convertOrLoad (value)
        return 1
    }

}

data class Jnz (val register : String, val offset : String) : Command {

    override fun apply (code : Code, state : State) : Int {
        fun offsetify (num : Int) =
            if        (num != 0) state.convertOrLoad (offset) else null

        val raw = register.number { toInt () }
        val reg = state[register]

        if (       raw != null) {
            return raw.let (::offsetify) ?: 1
        }

        return reg?.let (::offsetify) ?: 1
    }

}

data class Mpy (val register : String, val first : String, val second : String) : Command {

    override fun apply (code : Code, state : State) : Int {
        state[register] = state.convertOrLoad (first) * state.convertOrLoad (second)
        return 1
    }

}

data class Tgl (val register : String) : Command {

    override fun apply (code : Code, state : State) : Int {
        val                            number = state.load (register)
        val command = code[code.line + number] ?: return 1

        code[code.line + number] = when (command) {
            is Inc -> Dec (command.register, command.value)
            is Dec -> Inc (command.register, command.value)
            is Out -> Inc (command.register, "1")
            is Tgl -> Inc (command.register, "1")
            is Jnz -> Cpy (command.offset, command.register)
            is Cpy -> Jnz (command.supply, command.register)
            else   -> throw UnsupportedOperationException ()
        }

        return 1
    }

}

data class Out (val register : String) : Command {

    override fun apply (code : Code, state : State) : Int {
        return 1
    }

}