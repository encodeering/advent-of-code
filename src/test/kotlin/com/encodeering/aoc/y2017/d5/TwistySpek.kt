package com.encodeering.aoc.y2017.d5

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
class TwistySpek : Spek ({

    describe ("Twisty Trampoline") {

        describe ("#1") {

            it ("first example") {
                expect (twisty1 (mutableListOf (0, 3, 0, 1, -3))).to.equal (5)
            }

        }

        describe ("#2") {

            it ("second example") {
                expect (twisty2 (mutableListOf (0, 3, 0, 1, -3))).to.equal (10)
            }

        }

    }

})
