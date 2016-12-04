package com.encodeering.aoc.y2016.d3

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
class TriangleSpek : Spek ({

    describe ("Triangle") {

        describe ("#1") {

            it ("first example") {
                expect (triangle (5, 10, 25)).to.equal (null)
            }

        }

        describe ("#2") {

            it ("first example") {
                val description = """
                101 301 501
                102 302 502
                103 303 503
                201 401 601
                202 402 602
                203 403 603
                """

                expect (vertically (description.trimIndent ().lineSequence ())).to.equal (listOf (
                    Triple (101, 102, 103),
                    Triple (301, 302, 303),
                    Triple (501, 502, 503),
                    Triple (201, 202, 203),
                    Triple (401, 402, 403),
                    Triple (601, 602, 603)
                ))
            }

        }

    }

})