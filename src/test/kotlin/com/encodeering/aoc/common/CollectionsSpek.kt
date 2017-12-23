package com.encodeering.aoc.common

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
class CollectionsSpek : Spek ({

    describe ("Collections") {

        describe ("window") {

            it ("should create a windowed stream") {
                expect (emptyList<Int> ().asSequence ().window (2).toList ()).to.be.empty

                expect (listOf (1).asSequence ().window (2).toList ()).to.be.empty

                expect (listOf (1, 2).asSequence ().window (2).toList ()).to.equal (listOf (
                        listOf (1, 2)
                ))

                expect (listOf (1, 2, 3, 4, 5, 6, 7, 8, 9).asSequence ().window (3).toList ()).to.equal (listOf (
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

        describe ("blockwise") {

            it ("should create a windowed stream of equal size") {
                expect (emptyList<Int> ().asSequence ().blockwise (2).toList ()).to.be.empty

                expect (listOf (1).asSequence ().blockwise (2).toList ()).to.be.empty

                expect (listOf (1, 2).asSequence ().blockwise (2).toList ()).to.equal (listOf (
                        listOf (1, 2)
                ))

                expect (listOf (1, 2, 3, 4, 5, 6, 7, 8, 9).asSequence ().blockwise (2).toList ()).to.equal (listOf (
                        listOf (1, 2),
                        listOf (3, 4),
                        listOf (5, 6),
                        listOf (7, 8)
                ))
            }


            it ("should create a windowed stream with incomplete entries") {
                expect (emptyList<Int> ().asSequence ().blockwise (2, partial = true).toList ()).to.be.empty

                expect (listOf (1).asSequence ().blockwise (2, partial = true).toList ()).to.equal (listOf (
                        listOf (1)
                ))

                expect (listOf (1, 2).asSequence ().blockwise (2, partial = true).toList ()).to.equal (listOf (
                        listOf (1, 2)
                ))

                expect (listOf (1, 2, 3, 4, 5, 6, 7, 8, 9).asSequence ().blockwise (2, partial = true).toList ()).to.equal (listOf (
                        listOf (1, 2),
                        listOf (3, 4),
                        listOf (5, 6),
                        listOf (7, 8),
                        listOf (9)
                ))
            }

        }

        describe ("zipwise") {

            it ("should create a windowed stream of equal size with zipped elements") {
                expect (emptyList<Int> ().asSequence ().zipwise ().toList ()).to.be.empty

                expect (listOf (1).asSequence ().zipwise ().toList ()).to.be.empty

                expect (listOf (1, 2).asSequence ().zipwise ().toList ()).to.equal (listOf (
                        listOf (1, 2)
                ))

                expect (listOf (1, 2, 3, 4, 5, 6, 7, 8, 9).asSequence ().zipwise ().toList ()).to.equal (listOf (
                        listOf (1, 2),
                        listOf (2, 3),
                        listOf (3, 4),
                        listOf (4, 5),
                        listOf (5, 6),
                        listOf (6, 7),
                        listOf (7, 8),
                        listOf (8, 9)
                ))
            }

        }

        describe ("scan") {

            it ("should return a list of all intermediate steps") {
                expect (emptyList<Int> ().scan (0) { _, _ -> 0 }).to.be.empty

                expect (listOf (1).scan (10) { a, b -> a + b }).to.equal (listOf (11))

                expect (listOf (1, 2).scan (10) { a, b -> a + b }).to.equal (listOf (11, 13))
            }

        }


        describe ("reverse") {

            it ("should return a list with a reversed subsequence") {
                expect (listOf (1, 2, 3, 4, 5).reverse (0, 0)).to.equal (listOf (1, 2, 3, 4, 5))
                expect (listOf (1, 2, 3, 4, 5).reverse (0, 1)).to.equal (listOf (2, 1, 3, 4, 5))
                expect (listOf (1, 2, 3, 4, 5).reverse (0, 2)).to.equal (listOf (3, 2, 1, 4, 5))
                expect (listOf (1, 2, 3, 4, 5).reverse (0, 3)).to.equal (listOf (4, 3, 2, 1, 5))
                expect (listOf (1, 2, 3, 4, 5).reverse (0, 4)).to.equal (listOf (5, 4, 3, 2, 1))
                expect (listOf (1, 2, 3, 4, 5).reverse (0, 5)).to.equal (listOf (1, 2, 3, 4, 5))

                expect (listOf (1, 2, 3, 4, 5).reverse (3, 4)).to.equal (listOf (1, 2, 3, 5, 4))

                expect (listOf (1, 2, 3, 4, 5).reverse (4, 5)).to.equal (listOf (5, 2, 3, 4, 1))
                expect (listOf (1, 2, 3, 4, 5).reverse (4, 4)).to.equal (listOf (1, 2, 3, 4, 5))
                expect (listOf (1, 2, 3, 4, 5).reverse (4, 3)).to.equal (listOf (3, 2, 1, 5, 4))
                expect (listOf (1, 2, 3, 4, 5).reverse (4, 2)).to.equal (listOf (2, 1, 5, 4, 3))
                expect (listOf (1, 2, 3, 4, 5).reverse (4, 1)).to.equal (listOf (1, 5, 3, 4, 2))
                expect (listOf (1, 2, 3, 4, 5).reverse (4, 0)).to.equal (listOf (5, 2, 3, 4, 1))
            }

        }

    }

})