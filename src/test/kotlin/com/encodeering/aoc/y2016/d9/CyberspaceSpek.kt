package com.encodeering.aoc.y2016.d9

import com.encodeering.aoc.y2016.d9.Node.Compression
import com.encodeering.aoc.y2016.d9.Node.Literal
import com.encodeering.aoc.y2016.extension.asCharSequence
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
class CyberspaceSpek : Spek({

    describe ("Advent") {

        it ("sequence") {
            expect (sequenceOf ('a', 'b', 'c').asCharSequence ().toString ()).to.equal ("abc")
            expect (sequenceOf ('a', 'b', 'c').asCharSequence ().subSequence (0, 1).toString ()).to.equal ("abc".subSequence (0, 1).toString ())
        }

        describe ("node") {

            it ("literal") {
                expect (Literal ("hello").stringify ().toString ()).to.equal ("hello")
            }

            it ("compression") {
                expect (Compression (listOf (Literal ("hello"), Literal ("world")), 2).stringify ().toString ()).to.equal ("helloworldhelloworld")
            }

        }

        describe ("#1") {

            it ("first example") {
                expect (content ("ADVENT").toString ()).to.equal ("ADVENT")
                expect (content ("A(1x5)BC").toString ()).to.equal ("ABBBBBC")
                expect (content ("(3x3)XYZ").toString ()).to.equal ("XYZXYZXYZ")
                expect (content ("A(2x2)BCD(2x2)EFG").toString ()).to.equal ("ABCBCDEFEFG")
                expect (content ("(6x1)(1x3)A").toString ()).to.equal ("(1x3)A")
                expect (content ("X(8x2)(3x3)ABCY").toString ()).to.equal ("X(3x3)ABC(3x3)ABCY")
            }

        }

    }

})
