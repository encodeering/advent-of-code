package com.encodeering.aoc.y2016
import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Michael Clausen - encodeering@gmail.com
 */
@RunWith(JUnitPlatform::class)
class HelloSpek : Spek({

    describe ("Hello") {

        it ("should start with H") {
            "Hello".should.startWith ("H")
        }

    }

})
