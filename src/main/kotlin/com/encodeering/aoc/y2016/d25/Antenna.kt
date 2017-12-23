package com.encodeering.aoc.y2016.d25

import com.encodeering.aoc.common.State
import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.common.window
import com.encodeering.aoc.y2016.common.Assembunny
import com.encodeering.aoc.y2016.common.Out

/**
 * @author clausen - encodeering@gmail.com
 */
object Day25 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d25/signal.txt") {
            val code = Assembunny.parse (it)

            generateSequence (0) { it + 1 }.filter {
                val queue = mutableListOf<Int> ()

                Assembunny.lazy (code, State ().apply { this["a"] = it }) {
                          command ->
                    when (command) {
                        is Out -> queue += load (command.register)
                    }
                }.takeWhile { queue.size < 8 }.last ()

                queue.toList ().window (2, step = 2).filterNot { (a, b) -> a == 0 && b == 1 }.isEmpty ()
            }.take (1).forEach { println (it) }
        }
    }

}