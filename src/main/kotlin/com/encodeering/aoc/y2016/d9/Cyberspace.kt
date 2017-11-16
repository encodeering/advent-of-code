package com.encodeering.aoc.y2016.d9

import com.encodeering.aoc.common.times
import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.y2016.d9.Node.Compression
import com.encodeering.aoc.y2016.d9.Node.Literal

/**
 * @author clausen - encodeering@gmail.com
 */
object Day9 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/d9/file.txt") {
            println ("file length: ${size (it.first ())}")
        }

        traverse ("/d9/file.txt") {
            println ("file length: ${size (it.first (), true)}")
        }
    }

}


fun content (text: CharSequence, recursive : Boolean = false) = decompress (text, recursive).stringify ()

fun size (text: CharSequence, recursive : Boolean = false) = decompress (text, recursive).countify ()

fun decompress (text : CharSequence, recursive : Boolean) : Node {
    val regex = Regex ("""^([^(]+)?(?:\(([^)]+)\))?""")

    fun scan (text : CharSequence) : Iterable<Node> {

        val                              result = regex.find (text) ?: return listOf (Literal (text))
        val upcoming = text.subSequence (result.range.endInclusive + 1, text.length)

        val (meta, code) = result.destructured
        if        (code.isBlank ()) return listOf (Literal (meta))

        val (many, times) = code.split ("x", limit = 2).map(String::toInt)

        val content = upcoming.take (many).let {
            if (recursive) scan (it) else listOf (Literal (it))
        }

        return listOf (Literal (meta), Compression (content, times)) + scan (upcoming.drop (many))
    }

    return Compression (scan (text))
}

sealed class Node {

    data class Literal (val literal : CharSequence) : Node ()

    data class Compression (val children : Iterable<Node>, val times : Int = 1) : Node ()

}

fun Node.stringify () : CharSequence =
    when (this) {
        is Compression -> children.map (Node::stringify).joinToString ("") * times
        is Literal     -> literal
    }

fun Node.countify () : Long =
    when (this) {
        is Compression -> children.map (Node::countify).sum () * times
        is Literal     -> literal.length.toLong ()
    }
