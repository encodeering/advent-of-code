package com.encodeering.aoc.y2016.d14

import com.encodeering.aoc.common.primitive.md5sum
import java.lang.Math.max

/**
 * @author clausen - encodeering@gmail.com
 */
object Day14 {

    @JvmStatic
    fun main (args : Array<String>) {
        println ("normal: ${padkeys ("ngcjuoqr").keys.last ()}")
        println ("hardened: ${padkeys ("ngcjuoqr", 2016).keys.last ()}")
    }

}

fun padkeys (                salt : CharSequence, hardening : Int = 0) : Map<Int, String> {
    fun salt (num : Int) = "$salt$num"

    fun regex (count : Int, pattern : CharSequence = "a-z0-9") = max (0, count - 1).let { Regex ("""([$pattern])\1{$it}""", RegexOption.DOT_MATCHES_ALL) }

    val three = regex (3)

    val                   hashes = mutableMapOf<Int, String> ()
    fun hash (id : Int) = hashes.computeIfAbsent (id) { salt (id).md5sum (hardening) }

    return generateSequence (0) { it + 1 }
            .map    {
                it to hash (it)
            }
            .filter { (id,  md5) ->
                three.find (md5)?.run {
                    (id + 1..id + 999)
                            .any { idx -> regex (5, groupValues[1]).containsMatchIn (hash (idx))}
                } ?: false
            }
            .take (64).toMap ()
}