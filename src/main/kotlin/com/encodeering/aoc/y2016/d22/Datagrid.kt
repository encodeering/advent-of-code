package com.encodeering.aoc.y2016.d22

import com.encodeering.aoc.common.io.traverse
import com.encodeering.aoc.common.math.cartesian
import com.encodeering.aoc.common.math.matrix
import com.encodeering.aoc.common.search.Grid

/**
 * @author clausen - encodeering@gmail.com
 */
object Day22 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d22/datagrid.txt") {
            println ("viable pairs: ${df (it.drop (2)).viables ().sumBy { 1 }}")
        }

        traverse ("/y2016/d22/datagrid.txt") {
            println (df (it.drop (2)).display ())

            // 17 + 22 + 35 + 34 * 5
            // assumes all operations are valid
            // left, up, right, circle-toggle 34 times by 5 steps
        }
    }

}

fun df (stdout : Sequence<CharSequence> ) : Datagrid {
    return stdout.map { it.split (" ").filter { it.isNotBlank () } }.map { (path, size, used) ->
        Node (
            path,
            Node.amount (size),
            Node.amount (used)
        )
    }.sortedWith (
        compareBy (
            { it.y },
            { it.x }
        )
    ).toList ().run {
        Datagrid (Grid (last ().let { matrix (it.y + 1, it.x + 1) }))
    }
}

class Datagrid (private val grid : Grid<Node>) {

    fun display () : String {
        val viables = viables ().map { it.first }

        return grid.display (header = true, padding = 3) { (_, _, node) ->
            when {
                node.used == 0  -> "_"
                node in viables -> "."
                else            -> "#"
            }
        }
    }

    fun viables () = grid.values ().asIterable ().cartesian (true).filterNot { (a, b) -> a == b }.filter {
        (a, b) -> a.used > 0 &&
                  a.used <= b.avail
    }

}


data class Node (val path : String, val size : Int, val used : Int, val meta : Set<String> = setOf (path)) {

    init {
        assert (used <= size)
    }

    companion object {

        fun amount (value : String) = """(\d+)[T%]""".toRegex ().find (value)!!.groupValues[1].toInt ()

    }

    val avail : Int
        get () = size - used

    val x : Int by lazy {
        """.+node-x (\d+)-y\d+$""".toRegex ().find (path)!!.groupValues[1].toInt ()
    }

    val y : Int by lazy {
        """.+node-x\d+-y (\d+)$""".toRegex ().find (path)!!.groupValues[1].toInt ()
    }

}
