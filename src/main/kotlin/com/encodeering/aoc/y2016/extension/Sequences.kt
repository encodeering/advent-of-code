package com.encodeering.aoc.y2016.extension

import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author clausen - encodeering@gmail.com
 */

fun String.md5sum () : String {
    return BigInteger (1, MessageDigest.getInstance ("MD5").digest (toByteArray ())).toString (16).padStart (32, padChar = '0')
}

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