package com.encodeering.aoc.y2016.d11

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
class LaboratorySpek : Spek({

    describe ("Laboraty") {

        val hg = Code ("H", Type.Generator)
        val hm = Code ("H", Type.Microchip)

        val lg = Code ("L", Type.Generator)
        val lm = Code ("L", Type.Microchip)

        it ("fitness") {
            val laboratory = Laboratory (
                lift   = 0,
                floors = listOf (
                    Floor (0, listOf (hm, lm)),
                    Floor (1, listOf (hg)),
                    Floor (2, listOf (lg)),
                    Floor (3)
                )
            )

            val laboratory2 = Laboratory (
                lift   = 0,
                floors = listOf (
                    Floor (0, listOf (hm, lm)),
                    Floor (1, listOf (lg)),
                    Floor (2, listOf (hg)),
                    Floor (3)
                )
            )

            expect (morph(laboratory)).to.equal (morph(laboratory2))
        }

        describe ("#1") {

            it ("first example") {
                /*
                The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
                The second floor contains a hydrogen generator.
                The third floor contains a lithium generator.
                The fourth floor contains nothing relevant.
                */

                val laboratory = Laboratory (
                    lift   = 0,
                    floors = listOf (
                        Floor (0, listOf (hm, lm)),
                        Floor (1, listOf (hg)),
                        Floor (2, listOf (lg)),
                        Floor (3)
                    )
                )

                val solution = route (laboratory) {
                    lift == 3 && floor (3).run {
                        hg in items &&
                        hm in items &&
                        lg in items &&
                        lm in items
                    }
                }

                solution.forEach { println(it.display()); println() }

                expect (solution.size).to.equal (12)
            }

        }

    }

})