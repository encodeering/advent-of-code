package com.encodeering.aoc.y2016.common

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
class AssembunnySpek : Spek({

    describe ("Assembunny") {

        fun evaluate (description : String) : State {
            val state = State()
            val interpreter = Interpreter()
                interpreter.run (description.trimIndent().lineSequence(), state)

            return state
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

                val code = Interpreter().parse(description.trimIndent().lineSequence())
                    code.optimize()

                expect (code.world).to.equal (listOf (
                    Command.Inc ("z", "1"),
                    Command.Cpy ("c", "b"),
                    Command.Mpy ("a~", "c", "d"),
                    Command.Inc ("a", "a~"),
                    Command.Cpy ("a~", "0"),
                    Command.Cpy ("c", "0"),
                    Command.Cpy ("d", "0"),
                    Command.Dec ("z", "1")
                ))
            }

        }

    }

})