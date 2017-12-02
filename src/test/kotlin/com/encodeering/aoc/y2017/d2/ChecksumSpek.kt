package com.encodeering.aoc.y2017.d2

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
class ChecksumSpek : Spek ({

    describe ("Checksum") {

        describe ("#1") {

            it ("first example") {
                val description = """
                |5 1 9 5
                |7 5 3
                |2 4 6 8
                """

                expect (checksum (description.trimMargin ().lineSequence ())).to.equal (18)
            }

        }

        describe ("#2") {

            it ("second example") {
                val description = """
                |5 9 2 8
                |9 4 7 3
                |3 8 6 5
                """

                expect (checksum2 (description.trimMargin ().lineSequence ())).to.equal (9)
            }

        }

    }

})
