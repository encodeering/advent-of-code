package com.encodeering.aoc.y2016.d19

/**
 * @author clausen - encodeering@gmail.com
 */
object Day19 {

    @JvmStatic
    fun main(args : Array<String>) {
        println ("last standing elf: ${takesall (3004953)}")
    }

}


fun takesall (elves : Int) : Int {
    tailrec fun play (players : Iterable<Int>, beginner : Int) : Int {
        if           (players.count () == 1) return players.first ()

        val round = players.drop (beginner) +
                    players.take (beginner)

        val next = round.withIndex ().filter { (idx, _) -> idx % 2 == 0 }.map { it.value }

        val first0last = if (round.size % 2 == 0) 0 else next.size - 1

        return play (next, first0last)
    }

    return play ((0.until (elves)).toList (), 0) + 1
}