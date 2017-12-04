package com.encodeering.aoc.y2017.d4

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
class PhrasesSpek : Spek ({

    describe ("Phrases") {

        describe ("#1") {

            it ("first example") {
                expect (validate1 ("aa bb cc dd ee")).to.equal (true)
                expect (validate1 ("aa bb cc dd aa")).to.equal (false)
                expect (validate1 ("aa bb cc dd aaa")).to.equal (true)
            }

        }

    }

})
