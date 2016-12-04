package com.encodeering.aoc.y2016.io

import java.io.InputStreamReader

/**
 * @author clausen - encodeering@gmail.com
 */

fun <R> traverse                                                        (resource : String,      callback : (Sequence<String>) -> R) : R {
    return with (InputStreamReader (Any::class.java.getResourceAsStream (resource))) { useLines (callback) }
}