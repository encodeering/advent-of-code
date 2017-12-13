package com.encodeering.aoc.y2017.d13

import com.encodeering.aoc.common.findAll
import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d13/firewall.txt") {
        println ("severity #1: ${severity1 (it)}")
    }

    traverse ("/y2017/d13/firewall.txt") {
        println ("severity #2: ${severity2 (it)}")
    }
}

fun severity1   (lines : Sequence<String>) = severity (lines).first ().filter { it.position == 0 }.sumBy { it.number * it.depth }

fun severity2   (lines : Sequence<String>) = severity (lines).takeWhile { it.any { it.position == 0 } }.count ()

fun severity    (lines : Sequence<String>) : Sequence<List<Layer>> {
    val layers = lines.map {
        it.findAll ("(\\d+)".toRegex ()).toList ().let {
                   (layer,          depth) ->
             Layer (layer.toInt (), depth.toInt (), 0)
        }
    }.toList ()


    // could be optimized: layers of depth 2 will have a severity every second time
    return generateSequence (0) { it + 1 }.map { delay ->
        layers.map {
            layer ->
            layer.copy (position = (layer.position + run {
                val steps   = layer.depth - 1

                val value   = layer.number + delay

                val partial = value % steps
                val full    = value / steps

                if (full % 2 == 0)               partial
                else               layer.depth - partial - 1
            } % layer.depth))
        }
    }
}

data class Layer (val number : Int, val depth : Int, val position : Int)