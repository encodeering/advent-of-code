package com.encodeering.aoc.y2016.d5

import com.encodeering.aoc.common.md5sum

/**
 * @author clausen - encodeering@gmail.com
 */
object Day5 {

    @JvmStatic
    fun main (args : Array<String>) {
        println ("password: ${password ("ojvtpuvg")}")
        println ("password: ${password2 ("ojvtpuvg")}")
    }

}

fun password (door : String) : String {
    return generateSequence (0) { it + 1 }
            .map    { "$door$it".md5sum () }
            .filter { it.startsWith ("00000") }
            .map    { it[6 - 1] }
                .take   (8)
                    .joinToString ("")
}

fun password2 (door : String) : String {
    return generateSequence (0) { it + 1 }
            .map           { "$door$it".md5sum () }
            .filter        { it.startsWith ("00000") }
            .map           { it[6 - 1].run { if (this in '0'..'7') this.toString ().toInt ().run { this to it[7 - 1] } else null } }
            .filterNotNull ()
                .distinctBy { (idx, _) -> idx }
                .take (8)
                .sortedBy      { (idx, _) -> idx }
                .map           { (_  , c) -> c   }
                    .joinToString ("")
}
