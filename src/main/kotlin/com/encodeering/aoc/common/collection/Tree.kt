package com.encodeering.aoc.common.collection

/**
 * @author clausen - encodeering@gmail.com
 */
sealed class Node<out T, out M> (open val meta : Meta<M> = Meta ()) {

    fun <R> transform (transform : (Meta<M>, T) -> R,
                       aggregate : (Meta<M>, Iterable<R>) -> R) : R =
        when (this) {
            is Composite -> aggregate (meta, children.map { it.transform (transform, aggregate) })
            is Leaf      -> transform (meta, value)
        }

    data class Leaf<out T, out M> (val value : T, override val meta : Meta<M>) : Node<T, M>() {

        constructor (value : T, vararg meta : Pair<String, M>) : this (value, Meta (* meta))

    }

    data class Composite<out T, out M> (val children : Iterable<Node<T, M>>, override val meta : Meta<M>) : Node<T, M>() {

        constructor (children : Iterable<Node<T, M>>, vararg meta : Pair<String, M>) : this (children, Meta (* meta))

    }

}

class Meta<out M> (vararg values : Pair<String, M>) {

    private val                       mapping = values.associate { it }

    operator fun get (key : String) = mapping[key]!!

}

