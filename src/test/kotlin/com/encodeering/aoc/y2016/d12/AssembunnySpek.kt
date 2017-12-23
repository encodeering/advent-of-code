package com.encodeering.aoc.y2016.d12

import com.encodeering.aoc.y2016.common.Assembunny
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
class AssembunnySpek : Spek ({

    describe ("Assembunny") {

        describe ("#1") {

            it ("first example") {
                val description = """
                cpy 41 a
                inc a
                inc a
                dec a
                jnz a 2
                dec a
                """

                val code = Assembunny.parse (description.trimIndent ().lineSequence ())

                expect (Assembunny.run (code)["a"]).to.equal (42)
            }

        }

    }

})