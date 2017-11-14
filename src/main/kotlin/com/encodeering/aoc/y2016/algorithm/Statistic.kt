package com.encodeering.aoc.y2016.algorithm

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
