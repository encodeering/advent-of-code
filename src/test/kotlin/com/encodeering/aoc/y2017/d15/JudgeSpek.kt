package com.encodeering.aoc.y2017.d15

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
class JudgeSpek : Spek ({

    describe ("Judge") {

        describe ("#1") {

            it ("first example") {
                expect (duel1 (65, 8921)).to.equal (588)
            }

        }

        describe ("#2") {

            it ("first example") {
                expect (duel2 (65, 8921)).to.equal (309)
            }

        }

    }

})