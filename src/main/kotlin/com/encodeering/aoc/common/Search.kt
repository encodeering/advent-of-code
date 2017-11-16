package com.encodeering.aoc.common

/**
 * @author clausen - encodeering@gmail.com
 */
class Search<T, M, R> (
    private val storage  : () -> MutableCollection<Pair<T, R>>,
    private val generate : (T, R) ->      Iterable<Pair<T, R>>,
    private val morph    : (T)    -> M
) {

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