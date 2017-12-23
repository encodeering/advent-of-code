package com.encodeering.aoc.y2017.d11

import com.encodeering.aoc.common.collection.scan
import com.encodeering.aoc.common.io.traverse
import java.lang.Math.abs

/**
 * @author clausen - encodeering@gmail.com
 */
fun main (args : Array<String>) {
    traverse ("/y2017/d11/hexagon.txt") {
        println (hexagon1 (it.first ()))
    }

    traverse ("/y2017/d11/hexagon.txt") {
        println (hexagon2 (it.first ()))
    }
}

fun hexagon1 (movements : String) =
    HexCoord (0, 0, 0).run {
        hexCoord (this, movements.split (',')).last ().distance (this)
    }

fun hexagon2 (movements : String) =
    HexCoord (0, 0, 0).run {
        hexCoord (this, movements.split (',')).map { it.distance (this)!! }.max ()
    }

private fun hexCoord      (start : HexCoord, movements : List<String>) : List<HexCoord> {
    return movements.scan (start) { (x, y, z), direction ->
        when (direction) {
            "n"  -> HexCoord (x,     y - 1, z - 1)
            "s"  -> HexCoord (x,     y + 1, z + 1)
            "se" -> HexCoord (x + 1, y,     z + 1)
            "nw" -> HexCoord (x - 1, y,     z - 1)
            "ne" -> HexCoord (x + 1, y - 1, z)
            "sw" -> HexCoord (x - 1, y + 1, z)
            else -> throw IllegalStateException ("direction $direction unknown")
        }
    }
}

// http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
//
//         Y [y, z]
//
//
//  Z [x, y]        X [x, z]
//
private data class HexCoord (val x : Int, val y : Int, val z : Int) {

    fun distance (other : HexCoord) = listOf (
        abs (other.x - x),
        abs (other.y - y),
        abs (other.z - z)
    ).max ()

}
