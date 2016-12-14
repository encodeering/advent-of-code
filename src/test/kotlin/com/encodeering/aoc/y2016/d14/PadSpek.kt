package com.encodeering.aoc.y2016.d14

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
class PadSpek : Spek({

    describe ("Pad") {

        describe ("#1") {

            it ("first example") {
                expect (padkeys ("abc").keys.last ()).to.equal (22728)
            }

        }

    }

})