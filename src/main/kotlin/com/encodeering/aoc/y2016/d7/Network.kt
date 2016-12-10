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
            println ("addresses: ${ it.map (::tls).filter { it }.count () }")
        }

        traverse ("/d7/addresses.txt") {
            println ("addresses: ${ it.map (::ssl).filter { it }.count () }")
        }
    }

}

fun tls (addr : String) : Boolean = verify (addr, 4) {
    supernet, hypernet ->
    supernet.map { it.map (::abbafied).any { it } }.any {   it } &&
    hypernet.map { it.map (::abbafied).any { it } }.all { ! it }
}

fun ssl (addr : String) : Boolean = verify (addr, 3) {
    supernet, hypernet ->
    supernet.flatMap { it.filter (::abafied) }.any {
        var bab  = it.drop (1)
            bab += bab.first ()

        hypernet.filter { it.filter { s -> bab in s }.isNotEmpty () }.isNotEmpty()
    }
}

fun verify                   (addr : String, window : Int, process : (List<List<String>>, List<List<String>>) -> Boolean): Boolean {
    val (supernet, hypernet) = ip7 (addr).map (String::toList)
                                       .withIndex ()
                                           .partition { it.index % 2 == 0 }

    fun prepare (list : List<List<Char>>) = list.map { it.window (window).map { it.joinToString ("") } }

    return process (
            prepare (supernet.map { it.value }),
            prepare (hypernet.map { it.value })
    )
}

fun abbafied (it : String) : Boolean = it[0] == it[3] &&
                                       it[1] == it[2] &&
                                       it[0] != it[1]

fun abafied (it : String) : Boolean =  it[0] == it[2] &&
                                       it[0] != it[1]

fun ip7 (addr : String) = addr.split ("[").flatMap { it.split ("]") }