package com.encodeering.aoc.y2016.d16

/**
 * @author clausen - encodeering@gmail.com
 */
object Day16 {

    @JvmStatic
    fun main(args : Array<String>) {
        println ("dragon checksum: ${dragonfy ("00111101111101000", 272).checksum}")
    }

}

tailrec fun dragonfy  (content : CharSequence, length : Int = 2 * content.length + 1) : Dragon {
    val text = content.asSequence () + '0' + content.reversed ().map {
        when (it) {
            '0' -> '1'
            '1' -> '0'
            else -> it
        }
    }

    return if (text.count () >= length) Dragon (text.joinToString ("").take (length)) else dragonfy (text.joinToString (""), length)
}


data class Dragon (val text : CharSequence) {

    val checksum by lazy {
        tailrec fun build (text : CharSequence, step : Int = 0) : CharSequence {
            val odd =      text.length % 2 == 1
            if (odd && step > 0)
                return text

            val sum = 0.until  (text.length - if (odd) 1 else 0).step (2).map {
                when (text.subSequence (it, it + 2)) {
                    "00", "11" -> '1'
                    "01", "10" -> '0'
                    else -> throw IllegalStateException ()
                }
            }

            return build (sum.joinToString (""), step + 1)
        }

        build (text)
    }

}
