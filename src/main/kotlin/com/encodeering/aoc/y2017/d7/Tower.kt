package com.encodeering.aoc.y2017.d7

import com.encodeering.aoc.common.io.traverse
import com.encodeering.aoc.common.collection.zipwise
import java.lang.Math.abs
import kotlin.coroutines.experimental.buildSequence

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    traverse ("/y2017/d7/tower.txt") {
        val tower = construct (Paper.read (it))

        println ("root tower: ${tower.name}")
        println ("rebalance : ${rebalance (tower)}")
    }
}

fun rebalance    (tower : Tower) : Pair<String, Int> {
    val (_, unbalanced) = tower.locate { balance > 0 }.maxBy { it.first }!!

    return  unbalanced.towers.groupBy { it.totalweight }.run {
        val (anyweight, _)             = maxBy { it.value.size }!!
        val (offenderweight, offender) = minBy { it.value.size }!!

        with (offender[0]) {
            name to weight + anyweight - offenderweight
        }
    }
}

fun construct            (papers : List<Paper>) : Tower {
    tailrec fun perform  (papers : List<Paper>, towers : List<Tower>) : Tower {
        val conversions = papers.mapNotNull { paper ->
            when    (paper.disks.size) {
                0 -> paper to Tower (paper.name, paper.weight)

                else -> {
                    val subtowers = towers.filter { it.name in paper.disks }
                    if (subtowers.size != paper.disks.size) return@mapNotNull null

                    paper to Tower (
                        paper.name,
                        paper.weight,
                        subtowers
                    )
                }
            }
        }

        val                                               (removals,          additions) = conversions.unzip ()

        return if (conversions.size > 1) perform (papers - removals, towers + additions)
               else                                                           additions.first ()

    }

    return perform (papers, emptyList ())
}

data class Paper (val name : String, val weight : Int, val disks  : List<String>) {

    companion object {

        private val matcher = """(\w+)\s+\((\d+)\)(?:\s+->\s+(.+))?""".toRegex ()

        fun read  (line : Sequence<String>) : List<Paper> {
            return line.map {
                val   (name, weight,          disks) = matcher.matchEntire (it)!!.destructured

                Paper (name, weight.toInt (), disks.split (",").map { it.trim () }.filterNot { it.isBlank () })
            }.toList ()
        }

    }

}

data class Tower (val name : String, val weight : Int, val towers : List<Tower> = emptyList ()) {

    val totalweight : Int by lazy { weight + towers.sumBy { it.totalweight } }

    val balance : Int by lazy {
        val weights = towers.map { it.totalweight }
            weights.zipwise ().map { (a, b) -> abs (a - b) }.max () ?: 0
    }

}

fun Tower.locate (level : Int = 0, f: Tower.(Int) -> Boolean) : Sequence<Pair<Int, Tower>> {
    val root = this

    return buildSequence {
        if (f (level)) yield (level to root)

        yieldAll (root.towers.asSequence ().flatMap { it.locate (level + 1, f) })
    }
}