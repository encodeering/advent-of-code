package com.encodeering.aoc.common;

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

        it ("cartesian with other") {

            expect (listOf (1, 2, 3, 4).cartesian (listOf ('A', 'B'))).to.equal (listOf (
                1 to 'A', 1 to 'B',
                2 to 'A', 2 to 'B',
                3 to 'A', 3 to 'B',
                4 to 'A', 4 to 'B'
            ).sortedWith (compareBy ({ it.first }, { it.second })))

        }


        it ("permutation") {
            expect (listOf (1, 2, 3).permutation ().toList ()).to.equal (listOf (
                listOf (1, 2, 3),
                listOf (1, 3, 2),
                listOf (2, 1, 3),
                listOf (2, 3, 1),
                listOf (3, 1, 2),
                listOf (3, 2, 1)
            ))
        }
    }

})