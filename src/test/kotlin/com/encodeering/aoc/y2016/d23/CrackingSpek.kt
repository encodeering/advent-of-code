package com.encodeering.aoc.y2016.d23

import com.encodeering.aoc.y2016.algorithm.Interpreter
import com.encodeering.aoc.y2016.algorithm.State
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
class CrackingSpek : Spek({

    describe ("Cracking") {

        fun evaluate (description : String) : State {
            val state = State()
            val interpreter = Interpreter()
                interpreter.run (description.trimIndent().lineSequence(), state)

            return state
        }

        describe ("#1") {

            it ("first example") {
                val description = """
                cpy 2 a
                tgl a
                tgl a
                tgl a
                cpy 1 a
                dec a
                dec a
                """

                expect (evaluate (description)["a"]).to.equal (3)
            }

        }

    }

})
