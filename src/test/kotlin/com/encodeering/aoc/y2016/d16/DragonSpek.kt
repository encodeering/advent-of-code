package com.encodeering.aoc.y2016.d16

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
class DragonSpek : Spek ({

    describe ("Dragon") {

        describe ("#1") {

            it ("first example") {
                expect (dragonfy ("1").text).to.equal ("100")
                expect (dragonfy ("0").text).to.equal ("001")
                expect (dragonfy ("11111").text).to.equal ("11111000000")
                expect (dragonfy ("111100001010").text).to.equal ("1111000010100101011110000")
            }

            it ("second example") {
                expect (dragonfy ("110010110100", 12).checksum).to.equal ("100")
            }

            it ("third example") {
                expect (dragonfy ("10000", 20).checksum).to.equal ("01100")
            }

        }

    }

})