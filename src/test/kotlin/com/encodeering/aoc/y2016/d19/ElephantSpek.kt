package com.encodeering.aoc.y2016.d19

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
class ElephantSpek : Spek ({

    describe ("Elephant") {

        describe ("#1") {

            it ("first example") {
                expect (takesall (5)).to.equal (3)
            }

        }

        describe ("#2") {

            it ("first example") {
                expect (takesall2 (5)).to.equal (2)
            }

            it ("analysis") {
                (1..128).forEach {
                    println ("$it ${takesall2 (it)}")
                }
            }

            it ("analysis verification") {
                (1..128).forEach {
                    expect (takesall2 (it)).to.equal (takesall2math (it))
                }
            }

        }
    }

})