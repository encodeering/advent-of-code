package com.encodeering.aoc.y2017.d13

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
class FirewallSpek : Spek ({

    describe ("Firewall") {

        val description = """
        0: 3
        1: 2
        4: 4
        6: 4
        """.trimIndent ()

        describe ("#1") {

            it ("first example") {
                expect (severity1 (description.lineSequence ())).to.equal (0 * 3 + 6 * 4)
            }

        }

        describe ("#2") {

            it ("second example") {
                expect (severity2 (description.lineSequence ())).to.equal (10)

            }

        }

    }

})