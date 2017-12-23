package com.encodeering.aoc.common.search

import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Stack

/**
 * @author clausen - encodeering@gmail.com
 */
class Search<State, Hash, Result> (
    private val storage  : ()              -> MutableCollection<Pair<State, Result>>,
    private val generate : (State, Result) ->          Iterable<Pair<State, Result>>,
    private val morph    : (State)         -> Hash
) {

    companion object {

        fun <State,       Result> bfs (generate : (State, Result) -> Iterable<Pair<State, Result>>) = bfs (generate) { it }

        fun <State, Hash, Result> bfs (generate : (State, Result) -> Iterable<Pair<State, Result>>, morph : (State) -> Hash) = Search (
                storage = { LinkedList () },
                morph = morph,
                generate = generate
        )

        fun <State,       Result> dfs (generate : (State, Result) -> Iterable<Pair<State, Result>>) = dfs (generate) { it }

        fun <State, Hash, Result> dfs (generate : (State, Result) -> Iterable<Pair<State, Result>>, morph : (State) -> Hash) = Search (
                storage = { Stack () },
                morph = morph,
                generate = generate
        )

        fun <State,       Result> astar (comparator : Comparator<Pair<State, Result>>, generate : (State, Result) -> Iterable<Pair<State, Result>>) = astar (comparator, generate) { it }

        fun <State, Hash, Result> astar (comparator : Comparator<Pair<State, Result>>, generate : (State, Result) -> Iterable<Pair<State, Result>>, morph : (State) -> Hash) = Search (
                storage = { PriorityQueue (comparator) },
                morph = morph,
                generate = generate
        )

    }

    fun query (start : State, neutral : Result, solves : Pair<State, Result>.() -> Boolean) : Result? {
        val operations = storage ()
            operations += start to neutral

        val travels = mutableSetOf<Hash> ()

        do {
            val                op = operations.first ()
            operations.remove (op)

            val visited = morph (op.first)
            if (visited in travels) continue

            travels += visited

            if (op.solves ()) return op.second
            else
                 operations += generate (op.first, op.second).filterNot { (candidate, _) -> morph (candidate) in travels }
        } while (operations.isNotEmpty ())

        return operations.firstOrNull ()?.second
    }

}