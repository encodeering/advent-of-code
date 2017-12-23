package com.encodeering.aoc.y2016.common

import com.encodeering.aoc.common.language.State
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

        fun evaluate (description : String) : State {
            return Assembunny.run (Assembunny.parse (description.trimIndent ().lineSequence ()))
        }

        describe ("commands") {

            it ("addition") {
                val description = """
                cpy 2 a
                cpy 2 b
                inc a b
                """

                expect (evaluate (description)["a"]).to.equal (4)
            }

            it ("multiply") {
                val description = """
                cpy 0 a
                cpy 2 b
                mpy b 4 a
                """

                expect (evaluate (description)["a"]).to.equal (8)
            }

        }

        describe ("optimization") {

            it ("should optimize a multiplication pattern") {
                val description = """
                inc z
                cpy b c
                inc a
                dec c
                jnz c -2
                dec d
                jnz d -5
                dec z
                """

                val code = Assembunny.parse (description.trimIndent ().lineSequence ())
                    code.optimize ()

                expect (code.world).to.equal (listOf (
                    Inc ("z", "1"),
                    Cpy ("c", "b"),
                    Mpy ("a~", "c", "d"),
                    Inc ("a", "a~"),
                    Cpy ("a~", "0"),
                    Cpy ("c", "0"),
                    Cpy ("d", "0"),
                    Dec ("z", "1")
                ))
            }

        }

    }

})