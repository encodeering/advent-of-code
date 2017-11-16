package com.encodeering.aoc.y2016.d12

import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.y2016.common.Interpreter
import com.encodeering.aoc.y2016.common.State

/**
 * @author clausen - encodeering@gmail.com
 */
object Day12 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d12/instructions.txt") {
            val state = State ()

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }

        traverse ("/y2016/d12/instructions.txt") {
            val state = State ()
                state["c"] = 1

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }
    }

}