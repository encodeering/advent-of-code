package com.encodeering.aoc.y2017.d9

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
class GarbageSpek : Spek ({

    describe ("Garbage") {

        describe ("#1") {

            it ("first example") {
                expect (count1 ("{}")).to.equal (1)
                expect (count1 ("{{{}}}")).to.equal (6)
                expect (count1 ("{{},{}}")).to.equal (5)
                expect (count1 ("{{{},{},{{}}}}")).to.equal (16)
                expect (count1 ("{<a>,<a>,<a>,<a>}")).to.equal (1)
                expect (count1 ("{{<ab>},{<ab>},{<ab>},{<ab>}}")).to.equal (9)
                expect (count1 ("{{<!!>},{<!!>},{<!!>},{<!!>}}")).to.equal (9)
                expect (count1 ("{{<a!>},{<a!>},{<a!>},{<ab>}}")).to.equal (3)
            }

        }

        describe ("#2") {

            it ("first example") {
                expect (count2 ("<>")).to.equal (0)
                expect (count2 ("<<<<>")).to.equal (3)
                expect (count2 ("<{!>}>")).to.equal (2)
                expect (count2 ("<!!>")).to.equal (0)
                expect (count2 ("<!!!>>")).to.equal (0)
                expect (count2 ("<{o\"i!a,<{i<a>")).to.equal (10)
            }

        }

    }

})