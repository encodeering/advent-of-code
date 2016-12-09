package com.encodeering.aoc.y2016.extension

/**
 * @author clausen - encodeering@gmail.com
 */

fun CharSequence.times (n : Int) : CharSequence = (1.until (n)).fold (asSequence ()) { v, _ -> v + this@times.asSequence () }.asCharSequence ()

fun Sequence<Char>.asCharSequence () : CharSequence {
    return object : CharSequence {

        override val length : Int
            get () = this@asCharSequence.count ()

        override fun get (index : Int) : Char = this@asCharSequence.elementAt (index)

        override fun subSequence (startIndex : Int, endIndex : Int) : CharSequence = this@asCharSequence.drop (startIndex).take (endIndex).asCharSequence ()

        override fun toString () : String = this@asCharSequence.joinToString ("")

    }
}