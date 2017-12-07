package com.encodeering.aoc.y2017.d7

import com.encodeering.aoc.common.traverse
import com.encodeering.aoc.common.zipwise
import java.lang.Math.abs
import kotlin.coroutines.experimental.buildSequence

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d7/tower.txt") {
        val tower = construct (Paper.read (it))

        println ("root tower: ${tower.name}")
        println ("rebalance : ${rebalance (tower)}")
    }
}

fun rebalance    (tower : Tower) : Pair<String, Int> {
    val (_, unbalanced) = tower.locate { balance () > 0 }.maxBy { it.first }!!

    return  unbalanced.towers.groupBy { it.totalweight () }.run {
        val any        = maxBy { it.value.size }!!.value.first ()
        val offender   = minBy { it.value.size }!!.value.first ()
        val difference = unbalanced.balance ()

        offender.name to offender.weight + if (offender.totalweight () > any.totalweight ()) -difference else difference
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
                        subtowers.toList ()
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

    fun totalweight () : Int = weight + towers.sumBy { it.totalweight () }

    fun balance () : Int {
        val    weights = towers.map { it.totalweight () }
        return weights.zipwise ().map { (a, b) -> abs (a - b) }.max () ?: 0
    }

}

fun Tower.locate (level : Int = 0, f: Tower.(Int) -> Boolean) : Sequence<Pair<Int, Tower>> {
    val root = this

    return buildSequence {
        if (f (level)) yield (level to root)

        yieldAll (root.towers.asSequence ().flatMap { it.locate (level + 1, f) })
    }
}