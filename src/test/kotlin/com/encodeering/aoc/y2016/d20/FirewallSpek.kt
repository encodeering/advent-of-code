package com.encodeering.aoc.y2016.d20

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author clausen - encodeering@gmail.com
 */
@RunWith(JUnitPlatform::class)
class FirewallSpek : Spek({

    describe ("Firewall") {

        it ("extendable") {
            expect (extendable(1..2L, 2..3L)).to.equal (true)
            expect (extendable(2..3L, 1..2L)).to.equal (true)
            expect (extendable(1..1L, 2..3L)).to.equal (true)
            expect (extendable(2..3L, 1..1L)).to.equal (true)
            expect (extendable(1..1L, 3..3L)).to.equal (false)
            expect (extendable(3..3L, 1..1L)).to.equal (false)
        }

        it ("ip parsing"){
            expect (ipparse (0)).to.equal          (listOf (  0,   0,   0,   0))
            expect (ipparse (4294967295)).to.equal (listOf (255, 255, 255, 255))
        }

        describe ("#1") {

            it ("first example"){
                expect (ipfilter ("""
                    5-8
                    0-2
                    4-7
                """.trimIndent().splitToSequence("\n"))).to.equal (listOf (
                    0..2L,
                    4..8L
                ))
            }

        }

    }

})