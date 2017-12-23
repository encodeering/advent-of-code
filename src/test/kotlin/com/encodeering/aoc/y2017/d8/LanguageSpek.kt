package com.encodeering.aoc.y2017.d8

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
class LanguageSpek : Spek ({

    describe ("Language") {

        val description = """
        b inc 5 if a > 1
        a inc 1 if b < 5
        c dec -10 if a >= 1
        c inc -20 if c == 10
        """.trimIndent ()

        describe ("#1") {

            it ("first example") {
                expect (solve1 (Language.parse (description.lineSequence ()))).to.equal (1)
            }

        }

        describe ("#2") {

            it ("first example") {
                expect (solve2 (Language.parse (description.lineSequence ()))).to.equal (10)
            }

        }

    }

})