package com.encodeering.aoc.y2016.d23

import com.encodeering.aoc.common.State
import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.y2016.common.Assembunny

/**
 * @author clausen - encodeering@gmail.com
 */
object Day23 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d23/cracking.txt") {
            val code = Assembunny.parse(it)

            println ("register a: ${Assembunny.run (code, State().apply { this["a"] =  7 })["a"]}")
            println ("register a: ${Assembunny.run (code, State().apply { this["a"] = 12 })["a"]}")
        }
    }

}