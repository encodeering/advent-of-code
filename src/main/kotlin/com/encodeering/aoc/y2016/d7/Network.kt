package com.encodeering.aoc.y2016.d7

import com.encodeering.aoc.y2016.extension.window
import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day7 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/d7/addresses.txt") {
            println ("addresses: ${ it.map (::verify).filter { it }.count () }")
        }
    }

}

fun verify                   (addr : String) : Boolean {
    val (outer, inner) = ip7 (addr).map (String::toList)
                                           .map { it.window (4).map { it.joinToString ("") } }
                                           .map { it.map (::abbafied).any { it } }
                                               .withIndex ().partition    { it.index % 2 == 0 }

    return outer.any {   it.value } &&
           inner.all { ! it.value }
}

fun abbafied (it : String) : Boolean = it[0] == it[3] &&
                                       it[1] == it[2] &&
                                       it[0] != it[1]

fun ip7 (addr : String) = addr.split ("[").flatMap { it.split ("]") }