package com.encodeering.aoc.y2016.d20

import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.common.window

/**
 * @author clausen - encodeering@gmail.com
 */
object Day20 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/d20/table.txt") {
            println ("first ip: ${first (it)}")
        }

        traverse ("/d20/table.txt") {
            val max = 2 * Integer.MAX_VALUE.toLong()

            println ("allowed ips: ${all(it + "$max-$max")}")
        }
    }

}

fun first (ips : Sequence<CharSequence>) = ipfilter (ips).first ().last + 1

fun all (ips : Sequence<CharSequence>) = ipfilter (ips).window (2).sumBy { (a, b) -> (b.first - a.last - 1).toInt() }

fun ipfilter (ips : Sequence<CharSequence>) : List<LongRange> {
    return    ips.map { it.split ('-', ignoreCase = false, limit = 2) }
                 .map { it.map (String::toLong) }
                 .map { (a, b) -> a .. b }
                 .sortedWith (compareBy ({ it.first }, { it.last }))
                 .fold (listOf (0L .. 0L)) {
                     collection,                range ->
                     collection.last ().extend (range)?.run { collection.dropLast (1).plusElement (this) }
                                                      ?:run { collection.plusElement (range) }
                 }
}

fun ipparse (number : Long) = listOf (24, 16, 8, 0).map { number shr it and 0xFF }

fun extendable (a : LongRange, b : LongRange) : Boolean =
    a.start in b ||
    b.start in a ||
    a.last + 1 == b.start ||
    b.last + 1 == a.start

fun LongRange.extend (other : LongRange) = if (extendable (this, other)) minOf (first, other.first) .. maxOf (last, other.last) else null