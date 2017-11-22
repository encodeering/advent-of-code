package com.encodeering.aoc.y2016.d10

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
class BotSpek : Spek ({

    describe ("Bot") {

        describe ("#1") {

            it ("first example") {
                val description = """
                value 5 goes to bot 2
                bot 2 gives low to bot 1 and high to bot 0
                value 3 goes to bot 1
                bot 1 gives low to output 1 and high to bot 0
                bot 0 gives low to output 2 and high to output 0
                value 2 goes to bot 2
                """

                val instructions = description.trimIndent ().lineSequence ()

                val factory = Factory (compares (2, 5))
                    factory.execute (instructions)

                expect (factory.bin (0).chips).to.equal (listOf (5))
                expect (factory.bin (1).chips).to.equal (listOf (2))
                expect (factory.bin (2).chips).to.equal (listOf (3))
            }

        }

    }

})