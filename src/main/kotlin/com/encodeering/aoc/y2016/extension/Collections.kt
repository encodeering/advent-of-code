package com.encodeering.aoc.y2016.extension

/**
 * @author clausen - encodeering@gmail.com
 */

fun <T> List<T>.window  (n : Int, step : Int = 1) : List<List<T>> {
    return (0.rangeTo (count () - n).step (step)).asSequence ().fold (mutableListOf<List<T>> ()) {
        list,              idx ->
        list.add (subList (idx, idx + n))
        list
    }
}
