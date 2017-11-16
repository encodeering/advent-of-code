package com.encodeering.aoc.y2016.d23

import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.y2016.common.Interpreter
import com.encodeering.aoc.y2016.common.State

/**
 * @author clausen - encodeering@gmail.com
 */
object Day23 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/y2016/d23/cracking.txt") {
            val state = State ()
                state["a"] = 7

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }

        traverse ("/y2016/d23/cracking.txt") {
            val state = State ()
                state["a"] = 12

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }
    }

}