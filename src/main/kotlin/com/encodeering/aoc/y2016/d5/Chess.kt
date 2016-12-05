package com.encodeering.aoc.y2016.d5

import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author clausen - encodeering@gmail.com
 */
object Day5 {

    @JvmStatic
    fun main(args : Array<String>) {
        println ("password: ${password ("ojvtpuvg")}")
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

fun String.md5sum () : String {
    return BigInteger (1, MessageDigest.getInstance ("MD5").digest (toByteArray ())).toString (16).padStart (32, padChar = '0')
}