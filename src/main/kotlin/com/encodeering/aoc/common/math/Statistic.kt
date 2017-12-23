package com.encodeering.aoc.common.math

import com.encodeering.aoc.common.collection.nextOrNull

/**
 * @author clausen - encodeering@gmail.com
 */
fun <T> Iterable<T>.cartesian (symmetric : Boolean = false) : Iterable<Pair<T, T>> =
        this.withIndex ().flatMap {
                                                         (idx,            a) ->
            this@cartesian.drop (if (symmetric) 0 else idx).map { b -> a to b }
        }.distinct ()


fun <T, S> Iterable<T>.cartesian (other : Iterable<S>) : Iterable<Pair<T, S>> =
        flatMap {
                             a ->
            other.map { b -> a to b }
        }.distinct ()


fun <T> Collection<T>.permutation () : Sequence<List<T>> {
    if (size == 1) return sequenceOf (toList ())

    val outer = iterator ()

    var head : T? = null
    var inners = emptyList<Collection<T>> ().iterator ()

    fun next () : Collection<T>? = inners.nextOrNull () ?: outer.nextOrNull ()?.run {
        head   =                     this
        inners = (this@permutation - this).permutation ().iterator ()

        next ()
    }

    return generateSequence { next ()?.run { listOf (head!!) + this } }
}