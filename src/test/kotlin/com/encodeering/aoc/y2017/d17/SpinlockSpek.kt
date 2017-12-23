package com.encodeering.aoc.y2017.d17

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
class SpinlockSpek : Spek ({

    describe ("Spinlock") {

        describe ("#1") {

            it ("first example") {
                expect (spinlock1 (3, 2017)).to.equal (638)
            }

        }

    }

})