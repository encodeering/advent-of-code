package com.encodeering.aoc.y2016.d23

import com.encodeering.aoc.y2016.algorithm.Interpreter
import com.encodeering.aoc.y2016.algorithm.State
import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day23 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/d23/cracking.txt") {
            val state = State ()
                state["a"] = 7

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }

        traverse ("/d23/cracking.txt") {
            val state = State ()
                state["a"] = 12

            val interpreter = Interpreter ()
                interpreter.run (it, state)

            println ("register a: ${state["a"]}")
        }
    }

}