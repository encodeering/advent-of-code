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

fun severity1   (lines : Sequence<String>) = severity (lines).first ().let { (delay, firewall) -> firewall.severity  (delay) }

fun severity2   (lines : Sequence<String>) = severity (lines).takeWhile {    (delay, firewall) -> firewall.dangerous (delay) }.count ()

fun severity    (lines : Sequence<String>) : Sequence<Pair<Int, Firewall>> {
    val firewall = Firewall (lines.map {
        it.findAll ("(\\d+)".toRegex ()).toList ().let {
                   (layer,          depth) ->
             Layer (layer.toInt (), depth.toInt (), 0)
        }
    }.toList ())

    return generateSequence (0) { it + 1 }.map { it to firewall }
}

data class Firewall (private val  layers : List<Layer>) {

    fun dangerous (delay : Int) = layers.any { (it.number + delay) % (2 * it.depth - 2) == 0 }

    fun severity  (delay : Int) = layers.map {
        layer ->
        layer.copy (position = layer.run {
            val steps   = depth - 1

            val value   = number + delay

            val partial = value % steps
            val full    = value / steps

            if (full % 2 == 0)         partial
            else               depth - partial - 1
        })
    }.filter { it.position == 0 }.sumBy { it.number * it.depth }

}

data class Layer (val number : Int, val depth : Int, val position : Int)