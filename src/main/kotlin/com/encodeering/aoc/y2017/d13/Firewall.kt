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
}

fun severity1   (lines : Sequence<String>) = severity (lines)

fun severity    (lines : Sequence<String>) : Int {
    val layers = lines.map {
        it.findAll ("(\\d+)".toRegex ()).toList ().let {
                   (layer,          depth) ->
             Layer (layer.toInt (), depth.toInt (), 0)
        }
    }.toList ()

    return layers.map {
            layer ->
            layer.copy (position = (layer.position + run {
                val steps   = layer.depth - 1

                val value   = layer.number

                val partial = value % steps
                val full    = value / steps

                if (full % 2 == 0)               partial
                else               layer.depth - partial - 1
            } % layer.depth))
    }.filter { it.position == 0 }.sumBy { it.number * it.depth }
}

data class Layer (val number : Int, val depth : Int, val position : Int)