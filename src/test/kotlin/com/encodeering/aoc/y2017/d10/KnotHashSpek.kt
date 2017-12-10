package com.encodeering.aoc.y2017.d10

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
class KnotHashSpek : Spek ({

    describe ("KnotHash") {

        describe ("#1") {

            it ("first example") {
                expect (hash1 (listOf (0, 1, 2, 3, 4), listOf (3, 4, 1, 5))).to.equal (12)
            }

        }

        describe ("#2") {

            it ("first example") {
            }

        }

    }

})