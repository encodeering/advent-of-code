package com.encodeering.aoc.y2016.d1
import com.encodeering.aoc.y2016.d1.Direction.North
import com.encodeering.aoc.y2016.d1.Direction.South
import com.encodeering.aoc.y2016.d1.Direction.West
import com.encodeering.aoc.y2016.d1.Instruction.L
import com.encodeering.aoc.y2016.d1.Instruction.R
import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Michael Clausen - encodeering@gmail.com
 */
@RunWith(JUnitPlatform::class)
class HeadquarterSpek : Spek({

    describe ("Headquarter") {

        it ("direction") {
            expect (North.turn (L (0))).to.equal (West)
            expect (West.turn  (R (0))).to.equal (North)
        }

        describe ("#1") {

            it ("first example") {
                val       start = Point (0, 0, North)
                val end = start.move (R (2))
                               .move (L (3))

                expect (end.direction).to.equal (North)

                expect (end.x).to.equal (start.x + 2)
                expect (end.y).to.equal (start.y + 3)
            }

            it ("second example") {
                val       start = Point (0, 0, North)
                val end = start.move (R (2))
                               .move (R (2))
                               .move (R (2))

                expect (end.direction).to.equal (West)

                expect (end.x).to.equal (start.x)
                expect (end.y).to.equal (start.y - 2)

                expect (distance (start, end)).to.equal (2)
            }

            it ("third example") {
                val       start = Point (0, 0, North)
                val end = start.move (R (5))
                               .move (L (5))
                               .move (R (5))
                               .move (R (3))

                expect (end.direction).to.equal (South)

                expect (end.x).to.equal (start.x + 10)
                expect (end.y).to.equal (start.y +  2)

                expect (distance (start, end)).to.equal (12)
            }

        }

    }

})
