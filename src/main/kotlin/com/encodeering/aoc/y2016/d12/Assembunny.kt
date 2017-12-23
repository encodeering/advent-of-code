package com.encodeering.aoc.y2016.d12

import com.encodeering.aoc.common.State
import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.y2016.common.Assembunny

/**
 * @author clausen - encodeering@gmail.com
 */
object Day12 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d12/instructions.txt") {
            val code = Assembunny.parse (it)

            println ("register a: ${Assembunny.run (code, State ())["a"]}")
            println ("register a: ${Assembunny.run (code, State ().apply { this["c"] = 1 })["a"]}")
        }
    }

}