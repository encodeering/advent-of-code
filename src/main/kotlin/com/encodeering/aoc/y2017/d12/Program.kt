package com.encodeering.aoc.y2017.d12

import com.encodeering.aoc.common.traverse
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse("/y2017/d12/plumber.txt") {
        println (group (Program.read (it), 0).size)
    }

    traverse("/y2017/d12/plumber.txt") {
        println (blocks (Program.read (it), 0))
    }
}

fun blocks             (programs : List<Program>, start : Int) : Int {
    tailrec fun blocks (programs : List<Program>, start : Int, blocks : Int) : Int {
        if             (programs.isEmpty ()) return            blocks

        val rest = programs - group (programs, start)
        if (rest.isEmpty ())
            return blocks

        return blocks (rest, rest.first ().id, blocks + 1)
    }

    return blocks (programs, start, 1)
}

fun group        (programs : List<Program>, start : Int) : List<Program> {
    val mapping = programs.associate { it.id to it }

    fun Program.connections () = pipes.map { mapping[it]!! }

    val root = mapping[start]!!

    val seen = mutableListOf<Program> ()
        seen += root

    val nodes = LinkedList<Program> ()
        nodes += root.connections ()

    while         (nodes.isNotEmpty ()) {
        val node = nodes.poll ()

        if (node in seen) continue

        seen  += node
        nodes += node.connections ()
    }

    return seen.associate { it.id to true }.let {
                                     group ->
        programs.filter   { it.id in group }
    }
}

data class Program (val id : Int, val pipes : List<Int>) {

    companion object {

        private val matcher = """^(\d+) <-> (.+)$""".toRegex ()

        fun read  (line : Sequence<String>) : List<Program> {
            return line.map {
                val     (id,          pipes) = matcher.matchEntire (it)!!.destructured

                Program (id.toInt (), pipes.split (",").map { it.trim () }.filterNot { it.isBlank () }.map { it.toInt () })
            }.toList ()
        }

    }

}
