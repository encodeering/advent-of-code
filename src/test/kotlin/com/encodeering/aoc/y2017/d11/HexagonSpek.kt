package com.encodeering.aoc.y2017.d11

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
class HexagonSpek : Spek ({

    describe ("Hexagon") {

        describe ("#1") {

            it ("first example") {
                expect (hexagon1 ("ne,ne,ne")).to.equal (3)
                expect (hexagon1 ("ne,ne,sw,sw")).to.equal (0)
                expect (hexagon1 ("ne,ne,s,s")).to.equal (2)
                expect (hexagon1 ("se,sw,se,sw,sw")).to.equal (3)
            }

        }

    }

})