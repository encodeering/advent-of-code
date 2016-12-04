package com.encodeering.aoc.y2016.d4

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
class DecoySpek : Spek ({

    describe ("Decoy") {

        describe ("Code") {

            it ("statistics") {
                expect (Code.apply ("aaaaa-bbb-z-y-x-123[abxyz]").statistics).to.equal (mapOf (
                    'a' to 5,
                    'b' to 3,
                    'z' to 1,
                    'y' to 1,
                    'x' to 1
                ))
            }

        }

        describe ("#1") {

            it ("first example") {
                expect (verify ("aaaaa-bbb-z-y-x-123[abxyz]")?.sector).to.equal (123)
            }

            it ("second example") {
                expect (verify ("a-b-c-d-e-f-g-h-987[abcde]")?.sector).to.equal (987)
            }

            it ("third example") {
                expect (verify ("not-a-real-room-404[oarel]")?.sector).to.equal (404)
            }

            it ("forth example") {
                expect (verify ("totally-real-room-200[decoy]")).to.equal (null)
            }

        }

    }

})