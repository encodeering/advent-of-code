package com.encodeering.aoc.y2016.d18

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
class TrapSpek : Spek ({

    describe ("Trap") {

        describe ("#1") {

            it ("first example") {
                val plan = trapplan (".^^.^.^^^^", 10)

                expect (plan).to.equal ("""
                .^^.^.^^^^
                ^^^...^..^
                ^.^^.^.^^.
                ..^^...^^^
                .^^^^.^^.^
                ^^..^.^^..
                ^^^^..^^^.
                ^..^^^^.^^
                .^^^..^.^^
                ^^.^^^..^^
                """.trimIndent ())

                expect (plan.count { it == '.' }).to.equal (38)
            }

        }

    }

})