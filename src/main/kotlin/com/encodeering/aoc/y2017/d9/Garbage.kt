package com.encodeering.aoc.y2017.d9

import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d9/garbage.txt") {
        println ("groups: ${count1 (it.first ())}")
    }

    traverse ("/y2017/d9/garbage.txt") {
        println ("garbage: ${count2 (it.first ())}")
    }
}

fun count1 (line : CharSequence) : Int = count (line, object : TrackListener<Int> {

    val queue = mutableListOf<Int> ()
    var level = 0

    override fun build () = queue.sum ()

    override fun groupOpen  (line : CharSequence, idx : Int) { queue.add (++level) }

    override fun groupClose (line : CharSequence, idx : Int) {            --level  }

})

fun count2 (line : CharSequence) : Int = count (line, object : TrackListener<Int> {

    val queue = mutableListOf<Int> ()

    override fun build () = queue.sum ()

    override fun garbage  (line : CharSequence, from : Int, to : Int) {
        queue.add (shrink (line.subSequence (from, to)).length - 2)
    }

    private fun shrink (garbage : CharSequence) : String =
                        garbage.replace ("""(?<!!)(!!)*""".toRegex (), "")
                               .replace ("""![^!]""".toRegex(), "")
})

fun <T> count (line : CharSequence, listener : TrackListener<T>) : T {
    line.foldIndexed (Track (0, false)) { position, track, c ->
        when (track.garbage) {
            true  ->
                when (c) {
                    '>'  -> if (track.escaped) track.copy (escape = 0) else track.copy (escape = 0, garbage = false, start = position).also { listener.garbage (line, track.start, it.start + 1) }
                    '!'  ->                    track.copy (escape = track.escape + 1)
                    else ->                    track.copy (escape = 0)
                }

            false ->
                when (c) {
                    '<'   -> track.copy (escape = 0, garbage = true, start = position)
                    '{'   -> track.also { listener.groupOpen  (line, position) }
                    '}'   -> track.also { listener.groupClose (line, position) }
                     else -> track
                }
        }
    }

    return listener.build ()
}

data class Track (val escape : Int = 0, val garbage : Boolean, val start : Int = Integer.MIN_VALUE) {

    val escaped : Boolean get () = escape % 2 == 1

}

interface TrackListener<out T> {

    fun build () : T

    fun groupOpen    (line : CharSequence, idx : Int) {}

    fun groupClose   (line : CharSequence, idx : Int) {}

    fun garbage      (line : CharSequence, from : Int, to : Int) {}

}
