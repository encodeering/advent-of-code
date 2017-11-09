package com.encodeering.aoc.y2016.d25

import com.encodeering.aoc.y2016.algorithm.Interpreter
import com.encodeering.aoc.y2016.algorithm.State
import com.encodeering.aoc.y2016.extension.window
import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day25 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse("/d25/signal.txt") {
            val operations = it.toList()

            generateSequence (0) { it + 1 }.filter {
                val state = State ()
                    state["a"] = it

                var found = false
                val queue = mutableListOf<Int> ()

                Interpreter {
                                queue += it
                    val pairs = queue.toList ().window(2, step = 2).filterNot { (a, b) -> a == 0 && b == 1 }.take (50)

                    if (pairs.isNotEmpty ())
                        false
                    else
                        (queue.size < 100).also { found = ! it }
                }.run (operations.asSequence (), state)

                found
            }.take( 1).forEach { println(it) }
        }
    }

}