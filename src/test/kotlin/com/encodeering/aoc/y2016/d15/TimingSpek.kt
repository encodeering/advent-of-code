package com.encodeering.aoc.y2016.d15

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
class TimingSpek : Spek({

    describe ("Timing") {

        describe ("#1") {

            it ("first example") {
                val description = """
                Disc #1 has 5 positions; at time=0, it is at state 4.
                Disc #2 has 2 positions; at time=0, it is at state 1.
                """

                expect (unleash (description.trimIndent ().lineSequence ())).to.equal (5)
            }

        }

    }

})