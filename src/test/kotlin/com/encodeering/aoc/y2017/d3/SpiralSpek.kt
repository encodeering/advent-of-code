package com.encodeering.aoc.y2017.d3

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
class SpiralSpek : Spek ({

    describe ("Spiral") {

        describe ("#1") {

            it ("first example") {
                val     spiral = Spiral (25, 1, addone ())

                expect (spiral.shortest (1, 1)).to.equal  (0)
                expect (spiral.shortest (12, 1)).to.equal (3)
                expect (spiral.shortest (23, 1)).to.equal (2)
            }

            it ("second example") {
                val     spiral = Spiral (1024, 1, addone ())

                expect (spiral.shortest (1024, 1)).to.equal (31)
            }

            it ("spiral values") {
                fun Spiral.lastN (n : Int) = grid.values ().toList ().takeLast (n)

                expect (Spiral (20, 1, addone ()).lastN (6)).to.equal (listOf (10, 0,   0,  0,  0,  0))
                expect (Spiral (21, 1, addone ()).lastN (6)).to.equal (listOf (10, 21,  0,  0,  0,  0))
                expect (Spiral (22, 1, addone ()).lastN (6)).to.equal (listOf (10, 21, 22,  0,  0,  0))
                expect (Spiral (23, 1, addone ()).lastN (6)).to.equal (listOf (10, 21, 22, 23,  0,  0))
                expect (Spiral (24, 1, addone ()).lastN (6)).to.equal (listOf (10, 21, 22, 23, 24,  0))
                expect (Spiral (25, 1, addone ()).lastN (6)).to.equal (listOf (10, 21, 22, 23, 24, 25))
            }

        }

        describe ("#2") {

            it ("second example") {
                expect (Spiral (25, 1, adjacents ()).display()).to.equal (
                """|     147     142     133     122      59
                   |     304       5       4       2      57
                   |     330      10       1       1      54
                   |     351      11      23      25      26
                   |     362     747     806     880     931
                """.trimMargin ())
            }

        }

    }

})
