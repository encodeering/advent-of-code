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
class MathSpek : Spek ({

    val list4 = listOf (1, 2, 3, 4)
    val list6 = listOf (1, 2, 3, 4, 5, 6)

    describe ("Matrix") {

        describe ("creation") {

            it ("creation from a list") {
                val matrix = list4.matrix (2, 2)

                expect (matrix.n).to.equal (2)
                expect (matrix.m).to.equal (2)
                expect (matrix.size).to.equal (4)

                expect (matrix[0, 0]).to.equal (1)
                expect (matrix[0, 1]).to.equal (2)
                expect (matrix[1, 0]).to.equal (3)
                expect (matrix[1, 1]).to.equal (4)
            }

            it ("serialization back to list") {
                expect (list4.matrix (2, 2).toList ()).to.equal (list4)
            }

            it ("stringify") {
                expect (list6.matrix (2, 3).display ()).to.equal ("""
                | 1 2 3
                | 4 5 6
                """.trimMargin ())

                expect (list6.matrix (2, 3).display (header = true)).to.equal ("""
                |  0 1 2
                |0 1 2 3
                |1 4 5 6
                """.trimMargin ())
            }

        }

        describe ("transposition") {

            it ("of a vector") {
                val matrix = list4.matrix (1, 4).transpose ()

                expect (matrix.m).to.equal (4)
                expect (matrix.n).to.equal (1)

                expect (matrix.toList ()).to.equal (listOf (1, 2, 3, 4))
            }

            it ("of a mxn") {
                val matrix = list4.matrix (2, 2).transpose ()

                expect (matrix.m).to.equal (2)
                expect (matrix.n).to.equal (2)

                expect (matrix.toList ()).to.equal (listOf (1, 3, 2, 4))
            }

        }

        describe ("view") {

            it ("from 0, 0") {
                val matrix = list6.matrix (2, 3).matrix (1, 2)

                expect (matrix.m).to.equal (1)
                expect (matrix.n).to.equal (2)

                expect (matrix.toList ()).to.equal (listOf (1, 2))
            }

            it ("from 1, 0") {
                val matrix = list6.matrix (2, 3).matrix (1, 2, 1 to 1)

                expect (matrix.m).to.equal (1)
                expect (matrix.n).to.equal (2)

                expect (matrix.toList ()).to.equal (listOf (5, 6))
            }

        }

        describe ("rows") {

            it ("returns the given row as matrix") {
                list4.matrix (2, 2).row (0).let {
                    expect (it.n).to.equal (2)
                    expect (it.m).to.equal (1)

                    expect (it.toList ()).to.equal (listOf (1, 2))
                }
            }

        }

        describe ("columns") {

            it ("returns the given column as matrix") {
                list4.matrix (2, 2).column (0).let {
                    expect (it.n).to.equal (1)
                    expect (it.m).to.equal (2)

                    expect (it.toList ()).to.equal (listOf (1, 3))
                }
            }

        }

        describe ("transformation") {

            it ("point") {
                val matrix = list4.matrix (2, 2)

                expect (matrix.n).to.equal (2)
                expect (matrix.m).to.equal (2)

                expect (matrix.map { i, j, v -> i + j + v }.toList ()).to.equal (listOf (1, 3, 4, 6))
            }

            it ("line") {
                val matrix = list4.matrix (2, 2)

                expect (matrix.rotate (0 by 1, 1 by 1).toList ()).to.equal (listOf (2, 1, 4, 3))
                expect (matrix.rotate (0 by 1, 1 by 1, line = Matrix.Line.Column).toList ()).to.equal (listOf (3, 4, 1, 2))
            }

        }

    }

})
