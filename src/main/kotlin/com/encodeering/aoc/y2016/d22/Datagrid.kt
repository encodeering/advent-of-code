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

        traverse("/d22/datagrid.txt") {
            println (Grid (df (it.drop(2)).toList()).display ())

            // 17 + 22 + 35 + 34 * 5
            // assumes all operations are valid
            // left, up, right, circle-toggle 34 times by 5 steps
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

class Grid (val nodes : Iterable<Node>) {

    fun display () : String {
        val viables = nodes.viables ().map { it.first }

        return nodes.toXY ().entries.run {
            "  " + first ().value.joinToString ("  ") { it.position.first.toString().padStart(2) } +
            "\n" + joinToString ("\n") { (k, v) ->
                "$k".padStart (2) + v.joinToString ("  ") { node ->
                    when {
                        node.used == 0  -> "_"
                        node in viables -> "."
                        else            -> "#"
                    }.padStart (2)
                }
            }
        }
    }

}

fun Iterable<Node>.viables () = permutations (true).filterNot { (a, b) -> a == b }.filter {
    (a, b) -> a.used > 0 &&
              a.used <= b.avail
}

fun Iterable<Node>.toXY () = sortedWith (
    compareBy (
        { it.position.second },
        { it.position.first }
    )
).groupBy { it.position.second }

data class Node (val path : String, val size : Int, val used : Int, val meta : Set<String> = setOf (path)) {

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
