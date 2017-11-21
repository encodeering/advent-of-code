package com.encodeering.aoc.common

/**
 * @author clausen - encodeering@gmail.com
 */
data class Grid<T> (private val space : Matrix<T>)  {

    constructor (m : Int, n : Int, f : (Int, Int, Pair<Int, Int>) -> T) : this (matrixOf (m, n, f))

    operator fun get (i : Int, j : Int) =
        if (
            i in 0 until space.m &&
            j in 0 until space.n
        ) Sector (i, j, space[i, j]) else null

    fun <R> derive (f : Grid<T>.(Matrix<T>) -> Matrix<R>) : Grid<R> = Grid (f (space))

    fun values () =
        space.toSequence ()

    fun locate (                                                         f : Grid<T>.(Sector<T>) -> Boolean) =
        space.map { i, j, v -> Sector (i, j, v) }.toSequence ().filter { f (it) }

    fun display (      padding : Int = 0, header : Boolean = false, represent : (Sector<T>) -> CharSequence = { it.value.toString () }) =
        space.display (padding,           header) { i, j, v ->      represent   (Sector (i, j, v)) }

    fun neighbours (sector : Sector<T>, diagonally : Boolean = false) = neighbours (sector.i, sector.j, diagonally)

    fun neighbours (i : Int, j : Int, diagonally : Boolean = false) =
        run {
            val orthogonal = sequenceOf (
                this[i + 1, j    ],
                this[i - 1, j    ],
                this[i    , j + 1],
                this[i    , j - 1]
            )

            if (! diagonally)
                return@run orthogonal

            val diagonal = sequenceOf (
                this[i + 1, j + 1],
                this[i + 1, j - 1],
                this[i - 1, j + 1],
                this[i - 1, j - 1]
            )

            return@run orthogonal + diagonal
        }.filterNotNull ()

}

data class Sector<out T> (val i : Int, val j : Int, val value : T) {

    val ij by lazy { i to j }

}

fun <T> Iterable<Sector<T>>.toGrid () : Grid<T> {
    val map = sortedWith(
                  compareBy(
                        { it.i },
                        { it.j }
                  )
              ).groupBy { it.i }

    val m = map.keys.size
    val n = map.values.map { it.size }.max ()!!

    return Grid (matrixOf (m, n) { i, j, _ -> map[i]!![j].value })
}