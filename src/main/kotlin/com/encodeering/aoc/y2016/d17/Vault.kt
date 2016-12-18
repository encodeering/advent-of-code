package com.encodeering.aoc.y2016.d17

import com.encodeering.aoc.y2016.algorithm.Search
import com.encodeering.aoc.y2016.extension.md5sum
import java.util.LinkedList

/**
 * @author clausen - encodeering@gmail.com
 */
object Day17 {

    @JvmStatic
    fun main(args : Array<String>) {
        println ("vault path: ${Vault (4, 4).path (0 to 0, 3 to 3, "lpvhkcbi")!!}")
        println ("vault path: ${Vault (4, 4).longest (0 to 0, 3 to 3, "lpvhkcbi")?.length}")
    }

}

class Vault (val width : Int, val height : Int) {

    fun path (from : Pair<Int, Int>, to : Pair<Int, Int>, passcode : String) : CharSequence? {
        val search = Search (
            morph    = { it            },
            storage  = { LinkedList () },
            generate = this::generate
        )

        return search.query (State (passcode, from, true), "") { first.position == to }?.drop (passcode.length)
    }

    fun longest (from : Pair<Int, Int>, to : Pair<Int, Int>, passcode : String) : CharSequence? {
        var longest : CharSequence = ""

        val search = Search<State, State, CharSequence> (
            morph    = { it            },
            storage  = { LinkedList () },
            generate = { state, result -> generate (state, result).filterNot {
                (state, result) ->
                    if (state.position == to ) {
                        longest = if (state.code.length > longest.length) state.code else longest
                        true
                    }
                    else {
                        false
                    }
                }
            }
        )

        search.query (State (passcode, from, true), "") { false }

        return longest.drop (passcode.length)
    }

    private fun generate (state : State, @Suppress("UNUSED_PARAMETER") result : CharSequence) : Iterable<Pair<State, CharSequence>> {
        return state.code.md5sum ().take (4).withIndex ().map { (idx, c) ->
            when {
                c.isDigit () || c == 'a' -> idx to true
                else                     -> idx to false
            }
        }
            .map       { (direction, lock) ->
                when     (direction) {
                    0 -> state.copy (position = state.x     to state.y - 1, locked = lock, code = state.code + 'U')
                    1 -> state.copy (position = state.x     to state.y + 1, locked = lock, code = state.code + 'D')
                    2 -> state.copy (position = state.x - 1 to state.y,     locked = lock, code = state.code + 'L')
                    3 -> state.copy (position = state.x + 1 to state.y,     locked = lock, code = state.code + 'R')
                    else -> throw IllegalStateException ()
                }
            }
            .filterNot (State::locked)
            .filterNot {
                state -> (state.x < 0 || state.x >= width) ||
                         (state.y < 0 || state.y >= height)
            }.map {
                it to it.code
            }
    }


}

data class State (val code : String, val position : Pair<Int, Int>, val locked : Boolean) {

    val x : Int get () = position.first
    val y : Int get () = position.second

}