package com.encodeering.aoc.y2016.d21

import com.encodeering.aoc.common.reverse
import com.encodeering.aoc.common.rotate
import com.encodeering.aoc.common.swap
import com.encodeering.aoc.common.traverse
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
class ScramblerSpek : Spek ({

    describe ("Scrambler") {

        describe ("#1") {

            it ("commands") {
                expect ("abcde".swap (4, 0)).to.equal ("ebcda")
                expect ("edcba".reverse (0, 4)).to.equal ("abcde")
                expect ("abcde".rotate (-1)).to.equal ("bcdea")
                expect ("abcde".rotate ( 1)).to.equal ("eabcd")
                expect ("bcdea".move (1, 4)).to.equal ("bdeac")
            }

            it ("first example") {
                val description = """
                swap position 4 with position 0
                swap letter d with letter b
                reverse positions 0 through 4
                rotate left 1 step
                move position 1 to position 4
                move position 3 to position 0
                rotate based on position of letter b
                rotate based on position of letter d
                """

                expect (scramble ("abcde", description.trimIndent ().lineSequence ().toList ())).to.equal ("decab")
            }

        }

        describe ("#2") {

            it ("first example") {
                val description = """
                swap position 4 with position 0
                swap letter d with letter b
                reverse positions 0 through 4
                rotate left 1 step
                move position 1 to position 4
                move position 3 to position 0
                rotate based on position of letter b
                rotate based on position of letter d
                """

                expect (scramble ("decab", description.trimIndent ().lineSequence ().toList (), reverse = true)).to.equal ("abcde")
            }

            it ("second example") {
                traverse ("/y2016/d21/scrambling.txt") {
                    expect (scramble ("agcebfdh", it.toList (), reverse = true)).to.equal ("abcdefgh")
                }
            }

        }

    }

})