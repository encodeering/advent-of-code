package com.encodeering.aoc.y2016.d6

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
class RepetitionSpek : Spek({

    describe ("Repetition") {

        it ("transpose") {
            val codes = """
            12
            34
            56
            """.trimIndent ().lineSequence ().asIterable().map (String::asIterable).transpose ()

            expect (codes.elementAt (0).joinToString ("")).to.equal ("135")
            expect (codes.elementAt (1).joinToString ("")).to.equal ("246")
        }

        describe ("#1") {

            it ("first example") {
                val description = """
                eedadn
                drvtee
                eandsr
                raavrd
                atevrs
                tsrnev
                sdttsa
                rasrtv
                nssdts
                ntnada
                svetve
                tesnvt
                vntsnd
                vrdear
                dvrsen
                enarar
                """

                expect (correct (description.trimIndent ().lineSequence ())).to.equal ("easter")
            }

        }

    }

})