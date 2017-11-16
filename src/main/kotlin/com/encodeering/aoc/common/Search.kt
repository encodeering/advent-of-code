package com.encodeering.aoc.common

import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Stack

/**
 * @author clausen - encodeering@gmail.com
 */
class Search<T, M, R> (
    private val storage  : () -> MutableCollection<Pair<T, R>>,
    private val generate : (T, R) ->      Iterable<Pair<T, R>>,
    private val morph    : (T)    -> M
) {

    companion object {

        fun <T,    R> bfs (generate : (T, R) -> Iterable<Pair<T, R>>) = bfs (generate) { it }

        fun <T, M, R> bfs (generate : (T, R) -> Iterable<Pair<T, R>>, morph : (T) -> M) = Search (
            storage  = { LinkedList () },
            morph    = morph,
            generate = generate
        )

        fun <T,    R> dfs (generate : (T, R) -> Iterable<Pair<T, R>>) = dfs (generate) { it }

        fun <T, M, R> dfs (generate : (T, R) -> Iterable<Pair<T, R>>, morph : (T) -> M) = Search (
            storage  = { Stack () },
            morph    = morph,
            generate = generate
        )

        fun <T,    R> astar (comparator : Comparator<Pair<T, R>>, generate : (T, R) -> Iterable<Pair<T, R>>) = astar (comparator, generate) { it }

        fun <T, M, R> astar (comparator : Comparator<Pair<T, R>>, generate : (T, R) -> Iterable<Pair<T, R>>, morph : (T) -> M) = Search (
            storage  = { PriorityQueue (comparator) },
            morph    = morph,
            generate = generate
        )

    }

    fun query (start : T, neutral : R, solves : Pair<T, R>.() -> Boolean) : R? {
        val operations = storage ()
            operations += start to neutral

        val travels = mutableSetOf<M> ()

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