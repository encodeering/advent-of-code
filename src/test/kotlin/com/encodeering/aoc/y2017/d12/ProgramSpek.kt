package com.encodeering.aoc.y2017.d12

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
class ProgramSpek : Spek ({

    describe ("Program") {

        val description = """
        0 <-> 2
        1 <-> 1
        2 <-> 0, 3, 4
        3 <-> 2, 4
        4 <-> 2, 3, 6
        5 <-> 6
        6 <-> 4, 5
        """.trimIndent()

        describe ("#1") {

            it ("first example") {
                expect (group (Program.read (description.lineSequence ()), 0).size).to.equal (6)
            }

        }

        describe ("#2") {

            it ("second example") {
                expect (blocks (Program.read (description.lineSequence ()), 0)).to.equal (2)
            }

        }

    }

})