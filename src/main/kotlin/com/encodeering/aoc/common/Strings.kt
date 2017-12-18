package com.encodeering.aoc.common

import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author clausen - encodeering@gmail.com
 */

fun String.md5sum (repeat : Int = 0) : String {
    fun md5                                                                     (v : String) =
        BigInteger (1, MessageDigest.getInstance ("MD5").digest (v.toByteArray ())).toString (16).padStart (32, padChar = '0')

    return (0 until repeat).fold (md5 (this)) { v, _  -> md5 (v) }
}

fun <T : Number> String.number (f : String.() -> T) : T? = try {
    this.f ()
} catch (e : NumberFormatException) {
    null
}

fun CharSequence.findAll (regex : Regex) = regex.findAll (this).flatMap { it.groupValues.drop (1).asSequence () }

fun CharSequence.swap (idxA : Int, idxB : Int) : CharSequence = this.mapIndexed { idx, char ->
    when (idx) {
          idxA -> elementAt (idxB)
          idxB -> elementAt (idxA)
          else -> char
    }
}.joinToString ("")

fun CharSequence.reverse (idxA : Int, idxB : Int) : CharSequence = toList ().reverse (idxA, idxB).joinToString ("")

fun CharSequence.rotate (by : Int) : CharSequence = toList ().rotate (by).joinToString ("")

operator fun CharSequence.times (n : Int)        : CharSequence = (1 until n).fold             (asSequence ()) { v, _ -> v + this@times.asSequence () }.asCharSequence ()
operator fun Int.times (sequence : CharSequence) : CharSequence = (1 until this).fold (sequence.asSequence ()) { v, _ -> v +   sequence.asSequence () }.asCharSequence ()

fun Sequence<Char>.asCharSequence () : CharSequence {
    return object : CharSequence {

        private val content : String
            by lazy { joinToString ("") }

        override val length : Int
            by lazy { content.length }

        override fun get (index : Int) : Char = content[index]

        override fun subSequence (startIndex : Int, endIndex : Int) : CharSequence = content.subSequence (startIndex, endIndex)

        override fun toString () : String = content

    }
}
fun Iterable<Char>.asCharSequence () : CharSequence = asSequence ().asCharSequence ().run { toString () }

fun CharSequence.frequence () : Map<Char, Int> = groupBy { it }.mapValues { (_, v) -> v.size }