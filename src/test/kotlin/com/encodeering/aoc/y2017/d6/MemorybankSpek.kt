package com.encodeering.aoc.y2017.d6

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
class MemorybankSpek : Spek ({

    describe ("Memorybank") {

        describe ("#1") {

            it ("first example") {
                expect (reallocate (listOf (0, 2, 7, 0).toIntArray ())).to.equal (5)
            }

        }

    }

})