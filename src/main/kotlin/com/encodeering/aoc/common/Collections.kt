package com.encodeering.aoc.common

/**
 * @author clausen - encodeering@gmail.com
 */
fun <T> Iterator<T>.nextOrNull () = if (hasNext ()) next () else null

fun <T : Any>    Sequence<T>.window (n : Int, step : Int = 1, partial : Boolean = false) = window (n, step, partial) { it }
fun <T : Any, R> Sequence<T>.window (n : Int, step : Int = 1, partial : Boolean = false, transform : (List<T>) -> R) : Sequence<R> {
    val source  = iterator ()
    val windows = mutableMapOf<Int, List<T>> ().withDefault { emptyList () }

    fun xy (number : Int) = number % n to number / n

    return generateSequence (0) { it + step }.mapNotNull {
         start ->
        (start until start + n).map (::xy).mapNotNull { (x, y) ->
            windows.computeIfAbsent (y) { source.asSequence ().take (n).toList () }
            windows.getValue (y).getOrNull (x)
        }.also {
            val (x,                 y) = xy (start)
            if  (x == 0) (maxOf (0, y - 1) until y).forEach { windows.remove (it) }
        }
    }.takeWhile {
        when {
            partial -> it.isNotEmpty ()
            else    -> it.size == n
        }.also { if (! it) windows.clear () }
    }.map (transform)
}

fun <T : Any>    Sequence<T>.zipwise () = zipwise { it }
fun <T : Any, R> Sequence<T>.zipwise (transform : (List<T>) -> R) = window (2, 1, transform = transform)

fun <T : Any>    Sequence<T>.blockwise (n: Int, partial : Boolean = false) = blockwise (n, partial) { it }
fun <T : Any, R> Sequence<T>.blockwise (n: Int, partial : Boolean = false, transform : (List<T>) -> R) = window (n, n, partial, transform)

fun <T : Any>    Iterable<T>.window (n : Int, step : Int = 1, partial : Boolean = false) = window (n, step, partial) { it }
fun <T : Any, R> Iterable<T>.window (n : Int, step : Int = 1, partial : Boolean = false, transform : (List<T>) -> R) = asSequence ().window (n, step, partial, transform).asIterable ()

fun <T : Any>    Iterable<T>.zipwise () = zipwise { it }
fun <T : Any, R> Iterable<T>.zipwise (transform : (List<T>) -> R) = asSequence ().zipwise (transform).asIterable ()

fun <T : Any>    Iterable<T>.blockwise (n : Int, partial : Boolean = false) = blockwise (n, partial) { it }
fun <T : Any, R> Iterable<T>.blockwise (n : Int, partial : Boolean = false, transform : (List<T>) -> R) = asSequence ().blockwise (n, partial, transform).asIterable ()