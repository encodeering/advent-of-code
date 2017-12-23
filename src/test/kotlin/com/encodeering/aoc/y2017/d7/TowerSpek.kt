package com.encodeering.aoc.y2017.d7

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
class TowerSpek : Spek ({

    describe ("TowerSpek") {

        val description = """
        pbga (66)
        xhth (57)
        ebii (61)
        havc (66)
        ktlj (57)
        fwft (72) -> ktlj, cntj, xhth
        qoyq (66)
        padx (45) -> pbga, havc, qoyq
        tknk (41) -> ugml, padx, fwft
        jptl (61)
        ugml (68) -> gyxo, ebii, jptl
        gyxo (61)
        cntj (57)
        """.trimIndent ()

        describe ("#1") {

            it ("first example") {
                val tower = construct (Paper.read (description.lineSequence ()))

                expect (tower.name).to.equal ("tknk")
            }

        }

        describe ("#2") {

            it ("first example") {
                val tower = construct (Paper.read (description.lineSequence ()))

                expect (rebalance (tower)).to.equal ("ugml" to 60)
            }

        }

    }

})