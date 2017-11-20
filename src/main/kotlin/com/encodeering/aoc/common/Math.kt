package com.encodeering.aoc.common

import com.encodeering.aoc.common.Matrix.Companion.verify
import com.encodeering.aoc.common.Matrix.Line
import java.lang.Math.abs
import java.lang.Math.ceil
import java.lang.Math.floorMod
import java.lang.Math.log10

/**
 * @author clausen - encodeering@gmail.com
 */
interface Matrix<out T> {

    companion object {

        fun verify (value : Int, range : IntRange) = if (value in range) value else throw IllegalStateException ("$value is not within $range")

    }

    enum class Line { Row, Column }

    val m : Int
    val n : Int

    val size : Int get () = m * n

    operator fun get (i : Int, j : Int) : T

    fun display (padding : Int = 2, header : Boolean = false) : String {
        val alignment = ceil (log10 (m.toDouble ())).toInt ()
        val newline   = "\n"

        fun header () : String = if (header) "${" ".padStart (alignment)}${(0 until n).joinToString ("") { "$it".padStart (padding) }}$newline" else ""
        fun index (row : Int)  = if (header) "$row".padEnd   (alignment) else ""

        fun body () : String =
            (0 until m).joinToString (newline) {
                        i ->
                 index (i) + (0 until n).joinToString ("") { j -> "${get (i, j)}".padStart (padding) }
            }

        return header () + body ()
    }


}

data class ListMatrix<out T> (private val content : List<T>, override val m : Int, override val n : Int) : Matrix<T> {

    init {
        require (content.size == size)
    }

    override fun get (i : Int, j : Int) = content[n * i + j]

}


data class SubMatrix<out T> (private val content : Matrix<T>, private val ul : Pair<Int, Int>, private val lr : Pair<Int, Int>) : Matrix<T> {

    init {
        require (lr.first  >= ul.first)
        require (lr.second >= ul.second)
    }

    override val m : Int by lazy { lr.first  - ul.first  }
    override val n : Int by lazy { lr.second - ul.second }

    override fun get (i : Int, j : Int) = content[
        ul.first  + verify (i, 0 until m),
        ul.second + verify (j, 0 until n)
    ]

}

data class TransposeMatrix<out T> (private val matrix : Matrix<T>) : Matrix<T> {

    override val m : Int get () = matrix.n
    override val n : Int get () = matrix.m

    override fun get (i : Int, j : Int) = matrix[j, i]

}

data class TransformMatrix<out T, out R> (private val matrix : Matrix<T>, private val f : (Int, Int, T) -> R) : Matrix<R> {

    override val m : Int get () = matrix.m
    override val n : Int get () = matrix.n

    override fun get (i : Int, j : Int) = f (i, j, matrix[i, j])

}

fun <T> List<T>.matrix (m : Int, n : Int) : Matrix<T> = ListMatrix (this, m, n)

fun <T> Matrix<T>.matrix (m : Int, n : Int, point : Pair<Int, Int> = 0 to 0) : Matrix<T> = SubMatrix(this, point, point.first + m to point.second + n)

fun <T> Matrix<T>.toSequence () : Sequence<T> = generateSequence (0) { it + 1 }.takeWhile { it < size }.map { this[it / n, it % n] }
fun <T> Matrix<T>.toList () : List<T> = toSequence ().toList ()

fun <T> Matrix<T>.row    (i : Int) : Matrix<T> = ListMatrix ((0 until n).map { j -> this[i, j] }, 1, n)
fun <T> Matrix<T>.column (j : Int) : Matrix<T> = ListMatrix ((0 until m).map { i -> this[i, j] }, m, 1)
fun <T> Matrix<T>.transpose ()     : Matrix<T> = TransposeMatrix (this)

fun <T, R> Matrix<T>.map (f : (Int, Int, T) -> R) : Matrix<R> = TransformMatrix (this, f)
fun <T, R> Matrix<T>.map (line : Matrix.Line = Line.Row, f : Matrix<T>.(Int) -> List<R>) : Matrix<R> {
    val cache = mutableMapOf<Int, List<R>> ()

    return when (line) {
        Line.Row    -> TransformMatrix (this) { i, j, _ -> cache.computeIfAbsent (i) { f (i) }[j] }
        Line.Column -> TransformMatrix (this) { i, j, _ -> cache.computeIfAbsent (j) { f (j) }[i] }
    }
}

private fun <T> List<T>.rotate (by : Int) : List<T> = when {
    floorMod (by, size) == 0 -> this
              by <  0            -> takeLast (floorMod (size - abs (by), size)) + take (floorMod (abs (by), size))
    else                         -> takeLast (floorMod (abs (by), size)) + take (floorMod (size - abs (by), size))
}

fun <T> Matrix<T>.rotate (vararg rotations : Rotation, line : Matrix.Line = Line.Row) : Matrix<T> {
    val mapping = rotations.associate { it }

    return map (line) {                        idx ->
        when   (line) {
            Line.Row    -> this@rotate.row    (idx)
            Line.Column -> this@rotate.column (idx)
        }.toList ().run {
            mapping[idx]?.let { by -> rotate (by) } ?: this
        }
    }
}

typealias Rotation = Pair<Int, Int>

infix fun Int.by (by :Int) = Rotation (this, by)
