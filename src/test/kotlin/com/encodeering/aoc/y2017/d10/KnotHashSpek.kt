package com.encodeering.aoc.y2017.d10

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
class KnotHashSpek : Spek ({

    describe ("KnotHash") {

        describe ("#1") {

            it ("first example") {
                expect (hash1 (listOf (0, 1, 2, 3, 4), listOf (3, 4, 1, 5))).to.equal (12)
            }

        }

        describe ("#2") {

            it ("first example") {
                expect (hash2 ((0..255).toList (), "")).to.equal ("a2582a3a0e66e6e86e3812dcb672a272")
                expect (hash2 ((0..255).toList (), "AoC 2017")).to.equal ("33efeb34ea91902bb2f59c9920caa6cd")
                expect (hash2 ((0..255).toList (), "1,2,3")).to.equal ("3efbe78a8d82f29979031a4aa0b16a9d")
                expect (hash2 ((0..255).toList (), "1,2,4")).to.equal ("63960835bcdc130f0b66d7ff4f6a5a8e")
            }

        }

    }

})