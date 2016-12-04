package com.encodeering.aoc.y2016.d4

import com.encodeering.aoc.y2016.io.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day4 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/d4/encryptions.txt") {
            println ("sum of sectors: ${it.fold (0) { sum, text -> sum + (verify (text)?.sector ?: 0) }}")
        }
    }

}

val Alphabet = "abcdefghijklmnopqrstuvwxyz"

fun verify                             (text : String) : Code? {
    val              code = Code.apply (text)
    val statistics = code.statistics
    val checksum   = code.checksum

    tailrec fun verify (position : Int) : Boolean {
        if (            position >= checksum.length) return true

        val char   = checksum[position]

        val count  = statistics.replace (char, 0) ?: 0

        val larger = statistics.filterValues { it > count }
        if (larger.isNotEmpty()) return false

        val same = statistics.filterValues   { it == count }
        if (same.isNotEmpty ()) {
            if (position + 1 < checksum.length)
                if (Alphabet.indexOf (checksum[position]) >
                    Alphabet.indexOf (checksum[position + 1])) return false
        }

        return verify (position + 1)
    }

    return if (verify (0)) code else null
}

data class Code (val parts : List<String>, val sector : Int, val checksum : String) {

    companion object {

        fun apply(text : String) : Code {
            val checksum = text.substringAfter  ("[").substringBefore ("]")
            val parts    = text.substringBefore ("[").split ("-")

            return Code (parts.dropLast (1), parts.last ().toInt (), checksum)
        }

    }

    val statistics : MutableMap<Char, Int> get () {
        return parts.fold (mutableMapOf<Char, Int> ()) {
            map, word ->
                 word.forEach { c -> map[c] = map[c]?.plus (1) ?: 1 }
                 map
        }
    }

}