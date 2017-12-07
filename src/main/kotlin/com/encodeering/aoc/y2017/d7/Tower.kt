package com.encodeering.aoc.y2017.d7

import com.encodeering.aoc.common.traverse

/**
 * @author clausen - encodeering@gmail.com
 */
fun main(args : Array<String>) {
    traverse ("/y2017/d7/tower.txt") {
        val tower = construct (Paper.read (it))

        println ("root tower: ${tower.name}")
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

data class Tower (val name : String, val weight : Int, val towers : List<Tower> = emptyList ())
