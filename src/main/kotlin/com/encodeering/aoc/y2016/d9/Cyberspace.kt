package com.encodeering.aoc.y2016.d9

import com.encodeering.aoc.common.Node
import com.encodeering.aoc.common.Node.Composite
import com.encodeering.aoc.common.Node.Leaf
import com.encodeering.aoc.common.times
import com.encodeering.aoc.common.traverse

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

        val (many, times) = code.split ("x", limit = 2).map(String::toInt)

        val content = upcoming.take (many).let {
            if (recursive) scan (it) else listOf (CyberLeaf (it))
        }

        return listOf (CyberLeaf (meta), CyberComposite (content, "times" to times)) + scan (upcoming.drop (many))
    }

    return CyberComposite (scan (text), "times" to 1)
}

typealias CyberNode = Node<CharSequence, Int>
typealias CyberLeaf = Leaf<CharSequence, Int>
typealias CyberComposite = Composite<CharSequence, Int>

fun CyberNode.stringify () = transform ({ _, value -> value },                  { meta, children -> meta["times"] * children.joinToString ("") })
fun CyberNode.countify ()  = transform ({ _, value -> value.length.toLong () }, { meta, children -> meta["times"] * children.sum () })
