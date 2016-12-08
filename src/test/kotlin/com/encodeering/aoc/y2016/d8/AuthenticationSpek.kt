package com.encodeering.aoc.y2016.d8

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author clausen - encodeering@gmail.com
 */
@RunWith (JUnitPlatform::class)
class AuthenticationSpek : Spek({

    describe ("Authentication") {

        describe ("#1") {

            it ("parse") {
                expect (Code.parse ("rect 1x1")).to.equal (Code.Fill (1, 1))
                expect (Code.parse ("rotate row y=0 by 5")).to.equal (Code.Rotate (CodeTarget.Row, 0, 5))
                expect (Code.parse ("rotate column x=30 by 1")).to.equal (Code.Rotate (CodeTarget.Column, 30, 1))

            }

            it ("first example") {
                val     authentication = Authentication (3, 7).fill (3, 2).rotateCol (1, 1).rotateRow (0, 4).rotateCol (1, 1)

                println (authentication.display ())

                expect (authentication.display ().trim ()).to.equal (
                """
                .#..#.#
                #.#....
                .#.....
                """.trimIndent ().trim ())

                expect (authentication.lits ()).to.equal (6)
            }

        }

    }

})