package com.encodeering.aoc.y2017.d1

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
class CaptchaSpek : Spek ({

    describe ("Captcha") {

        describe ("#1") {

            it ("first example") {
                val step : Int = 1

                expect (captcha ("1122",     step = step)).to.equal (3)
                expect (captcha ("1111",     step = step)).to.equal (4)
                expect (captcha ("1234",     step = step)).to.equal (0)
                expect (captcha ("91212129", step = step)).to.equal (9)
            }

        }

    }

})
