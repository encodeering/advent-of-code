package com.encodeering.aoc.y2016.d7

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
class NetworkSpek : Spek({

    describe ("Network") {

        it ("split") {
            expect (ip7 ("lajiqiaxcxgpvsbu[bfetstragmoosbw]hzurjpxhecnadwosn[dquibbrojgurqiqd]aceccsnyrnfmcsd[quqxmzleqvspvsemjpn]noxrndgdrkffsdvh")).to.equal (listOf (
                    "lajiqiaxcxgpvsbu",
                    "bfetstragmoosbw",
                    "hzurjpxhecnadwosn",
                    "dquibbrojgurqiqd",
                    "aceccsnyrnfmcsd",
                    "quqxmzleqvspvsemjpn",
                    "noxrndgdrkffsdvh"
            ))
        }

        describe ("#1") {

            it ("first example") {
                expect (abba ("abba[mnop]qrst")).to.equal (true)
                expect (abba ("abcd[bddb]xyyx")).to.equal (false)
                expect (abba ("aaaa[qwer]tyui")).to.equal (false)
                expect (abba ("ioxxoj[asdfgh]zxcvbn")).to.equal (true)
            }

        }

        describe ("#2") {

            it ("second example") {
                expect (aba ("aba[bab]xyz")).to.equal (true)
                expect (aba ("xyx[xyx]xyx ")).to.equal (false)
                expect (aba ("aaa[kek]eke")).to.equal (true)
                expect (aba ("zazbz[bzb]cdb")).to.equal (true)
            }

        }

    }
})