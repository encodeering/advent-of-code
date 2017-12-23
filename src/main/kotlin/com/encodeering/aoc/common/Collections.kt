package com.encodeering.aoc.common

/**
 * @author clausen - encodeering@gmail.com
 */
fun <T> Iterator<T>.nextOrNull () = if (hasNext ()) next () else null

fun <T>    Sequence<T>.window (n : Int, step : Int = 1, partial : Boolean = false) = window (n, step, partial) { it }
fun <T, R> Sequence<T>.window (n : Int, step : Int = 1, partial : Boolean = false, transform : (T) -> R) : Sequence<List<R>> {
    val source  = iterator ()
    val windows = mutableMapOf<Int, List<T>> ().withDefault { emptyList () }

    fun xy (number : Int) = number % n to number / n

    return generateSequence (0) { it + step }.map {
         start ->
        (start until start + n).map (::xy).map { (x, y) ->
            windows.computeIfAbsent (y) { source.asSequence ().take (n).toList () }
            windows.getValue (y).getOrNull (x)
        }.filter { it != null }.also {
            val (x,                 y) = xy (start)
            if  (x == 0) (maxOf (0, y - 1) until y).forEach { windows.remove (it) }
        }
    }.takeWhile {
        when {
            partial -> it.isNotEmpty ()
            else    -> it.size == n
        }.also { if (! it) windows.clear () }
    }.map {
        it.filter { it != null }.map { transform (it!!) }
    }
}

fun <T>    Sequence<T>.zipwise () = zipwise { it }
fun <T, R> Sequence<T>.zipwise (transform : (T) -> R) = window (2, 1, transform = transform)

fun <T>    Sequence<T>.blockwise (n: Int, partial : Boolean = false) = blockwise (n, partial) { it }
fun <T, R> Sequence<T>.blockwise (n: Int, partial : Boolean = false, transform : (T) -> R) = window (n, n, partial, transform)

fun <T>    Iterable<T>.window (n : Int, step : Int = 1, partial : Boolean = false) = window (n, step, partial) { it }
fun <T, R> Iterable<T>.window (n : Int, step : Int = 1, partial : Boolean = false, transform : (T) -> R) = asSequence ().window (n, step, partial, transform).asIterable ()

fun <T>    Iterable<T>.zipwise () = zipwise { it }
fun <T, R> Iterable<T>.zipwise (transform : (T) -> R) = asSequence ().zipwise (transform).asIterable ()

fun <T>    Iterable<T>.blockwise (n : Int, partial : Boolean = false) = blockwise (n, partial) { it }
fun <T, R> Iterable<T>.blockwise (n : Int, partial : Boolean = false, transform : (T) -> R) = asSequence ().blockwise (n, partial, transform).asIterable ()

fun <T> List<T>.rotate (by : Int) : List<T> = when {
    Math.floorMod(by, size) == 0 -> this
              by <  0            -> takeLast (Math.floorMod(size - Math.abs(by), size)) + take (Math.floorMod(Math.abs(by), size))
    else                         -> takeLast (Math.floorMod(Math.abs(by), size)) + take (Math.floorMod(size - Math.abs(by), size))
}

fun <T, R> List<T>.scan (initial : R, map : (R, T) -> R) : List<R> {
    val scan = mutableListOf<R> ()

    fold (initial) {
             p, v ->
        map (p, v).also { scan += it }
    }

    return scan
}

fun <T> List<T>.reverse (idxA : Int = 0, idxB : Int = size - 1) : List<T> {
    val a = idxA % size
    val b = idxB % size

    val swaps = run {
        if (a <= b) (a .. b)
        else        (a .. b + size)
    }.map { it % size }.toList ()

    val mapping = (0 until swaps.size).associate {
        swaps[it] to
        swaps[swaps.size - it - 1]
    }

    return List (size) { mapping[it]?.let { this[it] } ?: this[it] }
}
