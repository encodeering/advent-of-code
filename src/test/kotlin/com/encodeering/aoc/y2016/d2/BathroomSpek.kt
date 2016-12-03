package com.encodeering.aoc.y2016.d2

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Michael Clausen - encodeering@gmail.com
 */
@RunWith(JUnitPlatform::class)
class BathroomSpek : Spek({

    describe ("Bathroom") {

        it ("center") {
            expect (center (2, 2)).to.equal (Pair (1, 1))
            expect (center (3, 3)).to.equal (Pair (1, 1))
            expect (center (4, 4)).to.equal (Pair (2, 2))
            expect (center (5, 5)).to.equal (Pair (2, 2))
        }

        describe ("#1") {

            it ("first example") {
                val description =
                """
                ULL
                RRDDD
                LURDL
                UUUUD
                """

                expect (decode (Panel (3, 3), description)).to.equal ("1985")
            }

        }

    }

})