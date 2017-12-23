package com.encodeering.aoc.y2016.d5

import com.encodeering.aoc.common.primitive.md5sum
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
class ChessSpek : Spek ({

    describe ("Chess") {

        it ("md5") {
            expect ("hello".md5sum ()).to.equal ("5d41402abc4b2a76b9719d911017c592")
        }

        describe ("#1") {

            it ("first example") {
                expect (password ("abc")).to.equal ("18f47a30")
            }

        }

        describe ("#2") {

            it ("first example") {
                expect (password2 ("abc")).to.equal ("05ace8e3")
            }

        }

    }

})