package com.encodeering.aoc.y2016.algorithm

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

        }

    }

})