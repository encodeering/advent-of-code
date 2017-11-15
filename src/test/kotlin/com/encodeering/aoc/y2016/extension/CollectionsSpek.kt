package com.encodeering.aoc.y2016.extension

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
class CollectionsSpek : Spek ({

    describe ("Collections") {

        describe ("window") {

            it ("should create a windowed stream") {
                expect (emptyList<Int> ().asSequence ().window (2).toList ()).to.be.empty

                expect (listOf (1).asSequence ().window (2).toList ()).to.be.empty

                expect (listOf (1, 2).asSequence ().window (2).toList ()).to.equal (listOf (
                        listOf (1, 2)
                ))

                expect (listOf (1, 2, 3, 4, 5, 6, 7, 8, 9).asSequence().window(3).toList ()).to.equal (listOf (
                        listOf (1, 2, 3),
                        listOf (2, 3, 4),
                        listOf (3, 4, 5),
                        listOf (4, 5, 6),
                        listOf (5, 6, 7),
                        listOf (6, 7, 8),
                        listOf (7, 8, 9)
                ))
            }

        }

    }

})