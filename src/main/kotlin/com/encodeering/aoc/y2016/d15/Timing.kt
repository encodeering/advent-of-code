package com.encodeering.aoc.y2016.d15

import com.encodeering.aoc.y2016.io.traverse
import java.lang.Math.floorMod

/**
 * @author clausen - encodeering@gmail.com
 */
object Day15 {

    @JvmStatic
    fun main(args : Array<String>) {
        traverse ("/d15/discs.txt") {
            println ("first index: ${unleash (it)}")
        }

        traverse ("/d15/discs.txt") {
            val cheat = "Disc #7 has 11 positions; at time=0, it is at state 0."

            println ("first index: ${unleash (it + cheat)}")
        }
    }

}

fun unleash (discs : Sequence<CharSequence>) : Int? {
    val                                      query = Regex("""(\d+)""")
    fun parse (instruction : CharSequence) = query.findAll (instruction).map (MatchResult::value).map (String::toInt).toList ()

    val machine = discs.map {
        parse (it).let {      (id,       max,        time,         state) ->
            Disc         (id = id, max = max, time = time, state = state)
        }
    }.sortedBy (Disc::id).withIndex ().map {
                        (idx,  disc) ->
        disc.reposition (idx + disc.time + 1)
    }.toList ()

    println ("time shift optimization: ${machine.joinToString (" - ")}")

    return generateSequence (0) { it + 1 }.filter {
                                     stamp ->
        machine.map { it.reposition (stamp) }.all { it.state == 0 }
    }.take (1).firstOrNull ()
}

data class Disc (val id: Int, val state : Int, val time : Int, val max : Int) {

    fun reposition (by : Int) =
        this.copy (
            time  =           by + time,
            state = floorMod (by + state, max)
        )

}