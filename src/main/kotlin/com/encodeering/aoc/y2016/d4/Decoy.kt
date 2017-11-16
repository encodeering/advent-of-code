package com.encodeering.aoc.y2016.d4

import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day4 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/y2016/d4/encryptions.txt") {
            println ("sum of sectors: ${it.fold (0) { sum, text -> sum + (verify (text)?.sector ?: 0) }}")
        }

        traverse ("/y2016/d4/encryptions.txt") {
            val      candidates = it.map (::verify).filterNotNull ().filter { c -> transpile (c)?.contains ("North", true) ?: false }
            println (candidates.map { "${transpile (it)} - ${it.sector}" }.joinToString ())
        }
    }

}

val Alphabet = "abcdefghijklmnopqrstuvwxyz"

fun transpile  (code: Code) : String? {
    fun shift                                  (c : Char) : Char {
        return with (Alphabet) { this[(indexOf (c) + code.sector) % length] }
    }

    return code.parts.map { p -> p.map (::shift).joinToString ("") }.joinToString (" ")
}

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