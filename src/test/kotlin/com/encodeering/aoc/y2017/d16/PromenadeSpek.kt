package com.encodeering.aoc.y2017.d16

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
class PromenadeSpek : Spek ({

    describe ("Promenade") {

        describe ("#1") {

            it ("first example") {
                expect ("abcde".promenade ("s1,x3/4,pe/b".split(','))).to.equal ("baedc")
            }

        }

    }

})