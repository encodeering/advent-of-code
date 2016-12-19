package com.encodeering.aoc.y2016.d19

import java.lang.Math.pow

/**
 * @author clausen - encodeering@gmail.com
 */
object Day19 {

    @JvmStatic
    fun main(args : Array<String>) {
        println ("last standing elf: ${takesall (3004953)}")
        println ("last standing elf: ${takesall2math (3004953)}")
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

fun takesall2 (elves : Int) : Int {
    tailrec fun play (players : Iterable<Int>, beginner : Int) : Int {
        if           (players.count() == 1) return players.first ()

        val round = players.drop (beginner) +
                    players.take (beginner)

        val next = round.withIndex ().filterNot { (idx, _) -> idx == Math.floor (players.count ().toDouble () / 2).toInt () }.map { it.value }

        return play (next, 1)
    }

    return play ((0.until (elves).toMutableList ()), 0) + 1
}

fun takesall2math (elves : Int) : Int {
    val upper = generateSequence  (0) { it + 1 }
                    .map    { pow (3.toDouble (), it.toDouble ()) }
                    .filter { it >= elves }
                    .take (1).first ()

    val lower  = upper / 3
    val middle = upper / 1.5

    if (elves <= middle) return (elves - lower).toInt ()
    else
        return (2 * elves - upper).toInt ()
}