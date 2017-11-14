package com.encodeering.aoc.y2016.algorithm;

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
class StatisticSpek : Spek({

    describe ("Statistic") {

        it ("cartesian") {

            expect (listOf (1, 2, 3, 4).cartesian (symmetric = false)).to.equal (listOf (
                1 to 1,
                1 to 2, 2 to 2,
                1 to 3, 2 to 3, 3 to 3,
                1 to 4, 2 to 4, 3 to 4, 4 to 4
            ).sortedWith (compareBy ({ it.first }, { it.second })))

        }

    }

})