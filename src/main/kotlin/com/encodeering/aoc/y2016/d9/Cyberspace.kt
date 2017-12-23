package com.encodeering.aoc.y2016.d9

import com.encodeering.aoc.common.io.traverse
import com.encodeering.aoc.common.primitive.times
import com.encodeering.aoc.y2016.d9.CyberNode.CyberComposite
import com.encodeering.aoc.y2016.d9.CyberNode.CyberLeaf

/**
 * @author clausen - encodeering@gmail.com
 */
object Day9 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d9/file.txt") {
            println ("file length: ${size (it.first ())}")
        }

        traverse ("/y2016/d9/file.txt") {
            println ("file length: ${size (it.first (), true)}")
        }
    }

}


fun content (text: CharSequence, recursive : Boolean = false) = decompress (text, recursive).stringify ()

fun size (text: CharSequence, recursive : Boolean = false) = decompress (text, recursive).countify ()

fun decompress (text : CharSequence, recursive : Boolean) : CyberNode {
    val regex = Regex ("""^([^(]+)?(?:\(([^)]+)\))?""")

    fun scan (text : CharSequence) : Iterable<CyberNode> {

        val                              result = regex.find (text) ?: return listOf (CyberLeaf (text))
        val upcoming = text.subSequence (result.range.endInclusive + 1, text.length)

        val (meta, code) = result.destructured
        if        (code.isBlank ()) return listOf (CyberLeaf (meta))

        val (many, times) = code.split ("x", limit = 2).map (String::toInt)

        val content = upcoming.take (many).let {
            if (recursive) scan (it) else listOf (CyberLeaf (it))
        }

        return listOf (CyberLeaf (meta), CyberComposite (times, content)) + scan (upcoming.drop (many))
    }

    return CyberComposite(1, scan(text))
}

sealed class CyberNode (val children : Iterable<CyberNode>) {

    class    CyberLeaf (val sequence : CharSequence)                          : CyberNode (emptyList ())
    class    CyberComposite (val times : Int, children : Iterable<CyberNode>) : CyberNode (children)

}

fun CyberNode.stringify () : CharSequence = when (this) {
    is CyberLeaf      -> sequence
    is CyberComposite -> times * children.joinToString ("") { it.stringify () }
    else -> throw IllegalStateException ()
}

fun CyberNode.countify () : Long = when (this) {
    is CyberLeaf      -> sequence.length.toLong ()
    is CyberComposite -> times.toLong () * children.fold (0L) { sum, node -> sum + node.countify () }
    else -> throw IllegalStateException ()
}