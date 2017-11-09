package com.encodeering.aoc.y2016.d22

import com.encodeering.aoc.y2016.algorithm.permutations
import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day22 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse("/d22/datagrid.txt") {
            println ("viable pairs: ${df (it.drop(2)).viables ().sumBy { 1 }}")
        }
    }

}

fun df (stdout : Sequence<CharSequence> ) : Iterable<Node> {
    return stdout.map { it.split (" ").filter { it.isNotBlank() } }.map { (path, size, used) ->
        Node (
            path,
            Node.amount (size),
            Node.amount (used)
        )
    }.toList ()
}

fun Iterable<Node>.viables () = permutations (true).filterNot { (a, b) -> a == b }.filter {
    (a, b) -> a.used > 0 &&
              a.used <= b.avail
}

data class Node (val path : String, val size : Int, val used : Int) {

    init {
        assert (used <= size)
    }

    companion object {

        fun amount (value : String) = """(\d+)[T%]""".toRegex ().find (value)!!.groupValues[1].toInt()

    }

    val avail : Int
        get () = size - used

    val position by lazy {
        val (x,            y) = """.+node-x(\d+)-y(\d+)$""".toRegex().find (path)!!.destructured
             x.toInt () to y.toInt ()
    }

}
