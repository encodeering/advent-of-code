package com.encodeering.aoc.common.math

import com.encodeering.aoc.common.math.Matrix.Companion.verify
import com.encodeering.aoc.common.math.Matrix.Line
import com.encodeering.aoc.common.collection.rotate
import java.lang.Math.ceil
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

    fun display (padding : Int = 2, header : Boolean = false, represent : (Int, Int, T) -> CharSequence = { _, _, v -> v.toString () }) : String {
        val alignment = ceil (log10 (m.toDouble ())).toInt ()
        val newline   = "\n"

        fun header () : String = if (header) "${" ".padStart (alignment)}${(0 until n).joinToString ("") { "$it".padStart (padding) }}$newline" else ""
        fun index (row : Int)  = if (header) "$row".padEnd   (alignment) else ""

        fun body () : String =
            (0 until m).joinToString (newline) {
                        i ->
                 index (i) + (0 until n).joinToString ("") { j -> represent (i, j, get (i, j)).padStart (padding) }
            }

        return header () + body ()
    }


}

data class FunctionMatrix<out T> (override val m : Int, override val n : Int, private val f : (Int, Int) -> T) : Matrix<T> {

    override fun get (i : Int, j : Int) = f (i, j)

}

data class ListMatrix<out T> (private val content : List<T>, override val m : Int, override val n : Int) : Matrix<T> {

    init {
        require (content.size == size) { "list size ${content.size} should match matrix size $size"}
    }

    override fun get (i : Int, j : Int) = content[n * i + j]

}


data class SubMatrix<out T> (private val content : Matrix<T>, private val partition : Partition) : Matrix<T> {

    init {
        require (
            partition.m >= 0 &&
            partition.n >= 0
        ) { "sub-matrix points should be defined with proper upper left ${partition.ul} and lower right ${partition.lr} pair"}

        require (
            partition.mrange.first + partition.m <= content.m &&
            partition.nrange.first + partition.n <= content.n
        ) { "sub-matrix should not exceed the wrapped matrix, using $partition" }
    }

    override val m : Int = partition.m
    override val n : Int = partition.n

    override fun get (i : Int, j : Int) = content[
        verify (partition.ul.first  + i, partition.mrange),
        verify (partition.ul.second + j, partition.nrange)
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

data class Partition (val m : Int, val n : Int, val ul : Pair<Int, Int> = 0 to 0) {

    val lr by lazy { ul.first + m - 1 to ul.second + n - 1 }

    val mrange by lazy { ul.first  .. lr.first  }
    val nrange by lazy { ul.second .. lr.second }

    operator fun contains (ij : Pair<Int, Int>) = ij.first in mrange && ij.second in nrange

}

fun <T> matrixOf (m : Int, n : Int, f : (Int, Int, Pair<Int, Int>) -> T) : Matrix<T> = FunctionMatrix (m, n) { i, j -> f (i, j, m to n) }

fun <T> List<T>.matrix (m : Int, n : Int) : Matrix<T> = ListMatrix (this, m, n)

fun <T> Matrix<T>.matrix (partition : Partition) : Matrix<T> = SubMatrix (this, partition)
fun <T> Matrix<T>.matrix (m : Int, n : Int, point : Pair<Int, Int> = 0 to 0) : Matrix<T> = matrix (Partition (m, n, point))

fun <T> Matrix<T>.toSequence () : Sequence<T> = (0 until size).asSequence ().map { this[it / n, it % n] }
fun <T> Matrix<T>.toList () : List<T> = toSequence ().toList ()

fun <T> Matrix<T>.row    (i : Int) : Matrix<T> = matrix (1, n, i to 0)
fun <T> Matrix<T>.column (j : Int) : Matrix<T> = matrix (m, 1, 0 to j)
fun <T> Matrix<T>.transpose ()     : Matrix<T> = TransposeMatrix (this)

fun <T> Matrix<T>.map (partition : Partition, f : (Int, Int, T) -> T) : Matrix<T> = map {
        i,   j,                       v ->
    if (i to j in partition) f (i, j, v) else v
}

fun <T> Matrix<T>.map (partition : Partition, line : Matrix.Line = Line.Row, f : Matrix<T>.(Int) -> List<T>) : Matrix<T> = map (line) { idx ->
    when (line) {
        Line.Row -> this@map.row (idx).toSequence ().toMutableList ().let {
            if                   (idx !in partition.mrange) return@let it

            val                                     replacement = f (this@map, idx).take (partition.n)
            partition.nrange.forEach { i -> it[i] = replacement[i - partition.nrange.first] }

            it
        }

        Line.Column -> this@map.column (idx).toSequence ().toMutableList ().let {
            if                         (idx !in partition.nrange) return@let it

            val                                     replacement = f (this@map, idx).take (partition.m)
            partition.mrange.forEach { i -> it[i] = replacement[i - partition.mrange.first] }

            it
        }
    }
}

fun <T, R> Matrix<T>.map (f : (Int, Int, T) -> R) : Matrix<R> = TransformMatrix (this, f)
fun <T, R> Matrix<T>.map (line : Matrix.Line = Line.Row, f : Matrix<T>.(Int) -> List<R>) : Matrix<R> {
    val cache = mutableMapOf<Int, List<R>> ()

    return when (line) {
        Line.Row    -> TransformMatrix (this) { i, j, _ -> cache.computeIfAbsent (i) { f (i) }[j] }
        Line.Column -> TransformMatrix (this) { i, j, _ -> cache.computeIfAbsent (j) { f (j) }[i] }
    }
}


fun <T> Matrix<T>.rotate (vararg rotations : Rotation, line : Matrix.Line = Line.Row) : Matrix<T> {
    val mapping = rotations.associate { it }

    return map (line = line) {                 idx ->
        when          (line) {
            Line.Row    -> this@rotate.row    (idx)
            Line.Column -> this@rotate.column (idx)
        }.toList ().run {
            mapping[idx]?.let { by -> rotate (by) } ?: this
        }
    }
}

typealias Rotation = Pair<Int, Int>

infix fun Int.by (by :Int) = Rotation (this, by)
