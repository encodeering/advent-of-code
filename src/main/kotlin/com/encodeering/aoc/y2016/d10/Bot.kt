package com.encodeering.aoc.y2016.d10

import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
object Day10 {

    @JvmStatic
    fun main (args : Array<String>) {
        traverse ("/y2016/d10/instructions.txt") {
            val factory = Factory (compares (17, 61))
                factory.execute (it)
        }

        traverse ("/y2016/d10/instructions.txt") {
            val factory = Factory ()
                factory.execute (it)

            println ("chip sum : ${(0..2).map (factory::bin).fold (1) { total, (_, chips) -> total * chips.sum () }}")
        }
    }

}

fun compares (a : Int, b : Int) : Bot.() -> Unit = {
    listOf   (a,       b).let { list ->
         if (low in list && high in list) println ("bot $id compares $low and $high")
    }
}

class Factory (val trace : Bot.() -> Unit = {}) {

    private val bots = mutableMapOf<Int, Bot> ()
    private val bins = mutableMapOf<Int, Bin> ()

    fun bot (id : Int) = bots.computeIfAbsent (id) { Bot (id) }
    fun bin (id : Int) = bins.computeIfAbsent (id) { Bin (id) }

    fun execute (instructions : Sequence<String>) {
        val                                      query = Regex ("""(\d+)""")
        fun parse (instruction : CharSequence) = query.findAll (instruction).map (MatchResult::value).map (String::toInt).toList ()

        fun instruct (instruction : CharSequence) : CharSequence? {
            return when {
                instruction.startsWith ("value") -> {
                    val (chip, botid) = parse (instruction)

                    bot (botid).apply {
                        handover (chip)
                    }

                    null
                }

                instruction.startsWith ("bot") -> {
                    val (botid, lowid, highid ) = parse (instruction)

                    bot (botid).run {
                        if (serving ()) {
                            trace ()
                            handover ({ low  }, if ("low to bot"  in instruction) bot (lowid)  else bin (lowid))
                            handover ({ high }, if ("high to bot" in instruction) bot (highid) else bin (highid))

                            return null
                        }

                        return instruction
                    }
                }

                else -> instruction
            }
        }

        instructions.toList ().apply {
            fun go (instructions : List<CharSequence>) : Unit = instructions.map (::instruct).filterNotNull ().let {
                if (!          it.isEmpty ())
                    return go (it)
            }

            go (this)
        }
    }
}

data class Bin (val id : Int, override val chips : MutableList<Int> = mutableListOf ()) : ChipAware

data class Bot (val id : Int, override val chips : MutableList<Int> = mutableListOf ()) : ChipAware  {

    val low : Int
        get () = chips.min ()!!

    val high : Int
        get () = chips.max ()!!

    fun serving () = chips.size >= 2

}

interface ChipAware {

    val chips : MutableList<Int>

}

fun ChipAware.handover (chip : Int) : ChipAware {
    chips += chip
    return this
}

fun ChipAware.handover (chip : ChipAware.() -> Int, that : ChipAware) : ChipAware {
    chip ().let {
        this.chips -= it
        that.chips += it
    }
    return this
}
