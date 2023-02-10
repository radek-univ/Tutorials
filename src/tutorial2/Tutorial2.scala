package tutorials
package tutorial2

import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.ArrayBuffer

class Tutorial2 extends AnyFunSuite {

  // Question 1
  // (a) What is the effect of the following procedure?
  // def swap(x: Int, y: Int) = { val t = x; x = y; y = t }

  // (b) What is the effect of the following procedure?
  def swapEntries(a: Array[Int], i: Int, j: Int): Unit = {
    val t = a(i)
    a(i) = a(j)
    a(j) = t
  }

  // (c) What is the effect of the following procedure?
  def magic(a: Array[Int], i: Int, j: Int): Unit = {
    if (i == j) return // a(i) = X, a(j) = Y
    a(i) ^= a(j) // a(i) = X + Y                        (bitwise, mod 2)
    a(j) ^= a(i) // a(j) = X + 2Y == X + 0 == X         (bitwise, mod 2)
    a(i) ^= a(j) // a(i) = (X + Y) + (X) == 2X + Y == Y (bitwise, mod 2)
  }

  test("What does magic function do?") {
    val arr = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    val arr2 = arr.clone()
    for ((i, j) <- Array((1, 8), (5, 3), (9, 0), (3, 3), (4, 9), (8, 7))) {
      swapEntries(arr, i, j)
      magic(arr2, i, j)
      assert(arr === arr2)
    }
  }

  // Question 2
  object SideEffects {
    var x = 3
    var y = 5

    def nasty(x: Int): Int = {
      y = 1
      2 * x
    }

    def main(args: Array[String]): Unit = println(nasty(x) + y)
  }

  // Question 3
  test("Is sort correct") {
    def quick(a: Array[String]): Array[String] = {
      if (a.length < 2)
        return a
      val pivot = a(0)
      quick(a.filter(_ < pivot)) appended pivot appendedAll quick(a.filter(_ > pivot))
    }

    val arrays = Array(
      "This is just some sentence in English.".split(" "),
      "Monday Tuesday Wednesday Thursday Friday Saturday Sunday".split(" "),
      Array("1", "1 0", "1 3", "1 2", "1  2")
    )
    for (array <- arrays) {
      val expected = quick(array)
      assert(array.sorted === expected)
      assert(array.sortWith(_ > _) === expected.reverse)
      val f = (x: String) => x.nonEmpty && x(0).isUpper
      assert(array.filter(f).sorted === expected.filter(f))
    }
  }

  // Question 4
  // Can we make this loop run a different number of steps than { numSteps, numSteps + 1, ∞ }?
  test("A loop using doubles") {
    val timeEnd: Double = 1.0
    val numSteps: Int = Int.MaxValue
    var actualNumSteps: BigInt = 0
    val timeStep: Double = (timeEnd / numSteps) / 8
    var time = 0.0
    while (time < timeEnd) { // Inv: 0 <= time <= timeEnd and time=k*timeStep for some k∈N
      time += timeStep
      actualNumSteps += 1
    }
    println(s"requested: $numSteps * 8, actual: $actualNumSteps")
  }

  // How to fix that loop once and for all?
  test("Fixed loop") {
    val timeEnd: Double = 1.0
    val numSteps: Int = Int.MaxValue
    var time = 0.0
    var i = 0
    while (i < numSteps) {
      time = timeEnd * i / numSteps
      i += 1
    }
  }

  // Question 5
  // All solutions were good!
  // Do you want to make a bonus challenge? See miscellaneous/rhymes/README.md

  // Question 6
  def minRecurrencePeriod(s: Array[Char]): Int = {
    var period = 1
    // I: period(s) != i for any 0 < i < period AND 0 < period <= s.length
    while (period < s.length) {
      var i = 0
      // I: s[0..i) == s[period..i+period) AND i + period <= s.length
      while (i + period < s.length && s(i) == s(i + period))
        i += 1
      if (i + period == s.length)
        return period
      period += 1
    }
    s.length
  }

  test("minRecurrencePeriodTest") {
    val testCases = Array(
      (" b bb b bb b bb b ", 5),
      (" b bbb b bb b bbb b ", 11),
      (" xx x x  x xx x x  x xx x ", 10),
      ("  x x  x  x x  x  x x  x  x ", 8),
      ("  x x  ", 5),
    )
    for ((s, expected) <- testCases) {
      val actual = minRecurrencePeriod(s.toArray)
      assert(
        actual === expected,
        s"\nFunction minRecurrencePeriod(\"$s\".toArray) should return $expected, got $actual."
      )
    }
  }

  def exists(p: Int => Boolean, N: Int): Boolean = {
    var i = 0
    // I: i <= N && forall j in [0, i) . !p(j)
    while (i < N && !p(i)) i += 1
    i < N
  }

  def sumOfReciprocals(fraction: (Int, Int)): Array[Int] = {
    var (p, q) = fraction
    require(p > 0, q > p)
    val res = ArrayBuffer[Int]()
    while (p != 0) {
      val m = (q - 1) / p + 1
      res += m
      p = m * p - q
      q = m * q
    }
    res.toArray
  }

  def prepareFraction(fraction: (Int, Int)): (String, String, String) = {
    val (p, q) = fraction
    val (numerator, denominator) = (p.toString, q.toString)
    val l = (numerator.length max denominator.length) + 2
    val center = (s: String) => " " * ((l - s.length + 1) / 2) + s + " " * ((l - s.length) / 2)
    (center(numerator), "-" * l, center(denominator))
  }

  def printReciprocals(a: Array[Int]): Unit = {
    val (t, m, b) = a.map { x => prepareFraction((1, x)) }.unzip3
    println(t.mkString("   ") + "\n" + m.mkString(" + ") + "\n" + b.mkString("   "))
  }

  def printFraction(fraction: (Int, Int)): Unit = {
    val (t, m, b) = prepareFraction(fraction)
    println(s"$t\n$m =\n$b")
  }

  test("Reciprocals") {
    val fraction = (3, 20)
    printFraction(fraction)
    println()
    printReciprocals(sumOfReciprocals(fraction))
  }

  // Question 9
  // There were few problems with this question.

  // Question 10
  def eval(a: Array[Double], x: Double): Double = {
    var i = 0
    var res = 0.0
    while (i < a.length) { // A x^3 + B x^2 + C x + D == (((A) x + B) x + C) x + D
      res *= x // Limiting the number of multiplications helps to reduce the rounding error of floating point computations
      res += a(i)
      i += 1
    }
    res
  }
}
