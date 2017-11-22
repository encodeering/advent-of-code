package com.encodeering.aoc.y2016.d13

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
class CubiclesSpek : Spek ({

    describe ("Cubicles") {

        describe ("#1") {

            it ("first example") {
                expect (Layout (7, 10, 10).display ().trim ()).to.equal ("""
                .#.####.##
                ..#..#...#
                #....##...
                ###.#.###.
                .##..#..#.
                ..##....#.
                #...##.###
                """.trimIndent ())
            }

        }

    }

})