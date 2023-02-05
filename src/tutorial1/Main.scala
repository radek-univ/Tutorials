package tutorials
package tutorial1

import scala.math.sqrt

object Main {
  //  square(...) can give unexpected results for bigger arguments (46341)
  def square(a: Int): BigInt = {
    a * a
  }

  def testSquare(): Unit = {
    val v = 46341
    println(s"The result for ${v} is ${square(v)}.") // interpolated strings
  }

  def square2(a: Int): BigInt = {
    val a2 = BigInt(a)
    a2 * a2
  }

  def testSquare2(): Unit = {
    val v = 46341
    println(s"The result for ${v} is ${square2(v)}.")
  }

  //  Mathematician-friendly modulo operation?
  def modulus1(x: Int): Int = {
    x % 3
  }

  def modulus2(x: Int): Int = {
    x - 3 * (x / 3)
  }

  def modulus3(x: Int): Int = {
    if (x >= 0) x % 3 else -(x % 3)
  }

  def modulus4(x: Int): Int = {
    val res = x % 3
    if (res < 0) res + 3 else res
  }

  def testModulus(): Unit = {
    for (v <- -10 to 10) {
      print(f"v = $v%3s: ")
      for (f <- List(modulus1 _, modulus2 _, modulus3 _, modulus4 _)) {
        val result = f(v)
        print(f"| $result%6s")
      }
      println()
    }
  }

  def biggestSquare(n: Int): Int = {
    require(n >= 0)
    var i: BigInt = 0 // if this is an Int, the loop will never end for n = Int.MaxValue
    while (i * i <= n) {
      i += 1
    }
    (((i - 1) * (i - 1))).toInt
  }

  def testBiggestSquare(): Unit = {
    val v = Int.MaxValue
    println(s"biggest square <= ${v} is ${biggestSquare(v)}")
  }

  def biggestSquare2(n: Int): Int = {
    val m = sqrt(n).floor.toInt
    m * m
  }

  def notASuspiciousLoopAtAll(from: Double, to: Double, increment: Double): Unit = {
    require(increment > 0)
    var current = from
    while (current <= to) {
      current += increment;
      print("A")
    }
  }

  def testALoop(): Unit = {
    val increment: Double = 0.00000001 // Keep adding zeros both here
    val from: Double = 1
    val to: Double = 1.00000005 // and here to observe the dangers of Double
    notASuspiciousLoopAtAll(from, to, increment) // How many letters A will be printed?
  }

  def findSum(a: Array[Int]): Int = {
    var res = 0
    var i = a.length - 1
    while (i >= 0) {
      res += a(i);
      i -= 1
    }
    res
  }

  def testFindSum(): Unit = {
    println(findSum(Array(1, 2, 3)))
  }

  def findMax(a: Array[Int]): Int = {
    require(a.length > 0)
    var res = a(0)
    var i = 1
    while (i < a.length) {
      if (a(i) > res)
        res = a(i)
      i += 1
    }
    res
  }

  // How to use Option type?
  val l: List[Int] = List(1, 2, 3, 4, 5)

  def head(l: List[Int]): Option[Int] = l match {
    case h :: t => Some(h)
    case Nil => None
  }

  def add(n: Option[Int], m: Option[Int]): Option[Int] = (n, m) match {
    case (Some(a), Some(b)) => Some(a + b)
    case (Some(a), None) => Some(a)
    case (None, Some(b)) => Some(b)
    case _ => None
  }

  // What can be improved about this program?
  object Milk {
    def findSum(a: Array[Int]): Int = {
      val n = a.length
      var total = 0;
      var i = 0
      while (i < n) {
        total += a(i)
        i += 1
      }
      total
    }

    def main(args: Array[String]) = {
      val n = args.length
      val a = new Array[Int](n)
      for (i <- 0 until n) a(i) = args(i).toInt
      println(findSum(a))
    }
  }

  // Slowest fib
  def fib(n: Int): Int = {
    require(n >= 0)
    if (0 to 1 contains n) {
      n
    } else {
      fib(n - 2) + fib(n - 1)
    }
  }

  def fasterFib(n: Int): BigInt = {
    require(n >= 0)
    if (n == 0)
      return 0

    var (k, x, y) = (1: BigInt, 0: BigInt, 1: BigInt)

    while (k < n) {
      k += 1
      val oldY = y
      y = x + y;
      x = oldY
    }
    y
  }

  def testFib(): Unit = {
    for (n <- 0 to 4500000 by 200000) {
      val startTime = System.nanoTime()
      val res = fastestFib(n)
      val endTime = System.nanoTime()
      val time = (endTime - startTime) / 1e9d
      println(s"It took ${time} seconds to compute fastestFib(${n}).")
      println("█" * (time * 20).ceil.toInt)
    }
  }

  def fastestFib(n: Int): BigInt = {
    require(n >= 0)
    type Matrix2x2 = (BigInt, BigInt, BigInt, BigInt)

    def mul(A: Matrix2x2, B: Matrix2x2): Matrix2x2 = {
      (
        A._1 * B._1 + A._2 * B._3, A._1 * B._2 + A._2 * B._4,
        A._3 * B._1 + A._4 * B._3, A._3 * B._2 + A._4 * B._4
      )
    }

    var M: Matrix2x2 = (1, 1, 1, 0)
    var res: Matrix2x2 = (1, 0, 0, 1)
    var i = n
    while (i > 0) {
      if (i % 2 == 1) res = mul(res, M)
      M = mul(M, M)
      i /= 2
    }
    res._2
  }

  def extendedEuclidAlgorithm(n: Int, m: Int): (Int, Int, Int) = {
    require(n > 0 && m > 0)
    var (a, b) = (m, n)
    var (p, q) = (1, 0)
    var (r, s) = (0, 1)

    // I: gcd(a,b) = gcd(m,n)  &&  a,b >= 0  &&  a = pm + qn  &&  b = rm + sn
    while (b != 0) {
      val Q = a / b
      val (new_a, new_b) = (b, a - Q * b)
      // new_b == a - Q * b == pm + qn - Q * (rm + sn) = m(p-Q*r) + n(q-Q*s)
      val (new_r, new_s) = (p - Q * r, q - Q * s)

      a = new_a;
      b = new_b;
      p = r;
      q = s;
      r = new_r;
      s = new_s
    }
    (a, p, q)
  }

  def main(args: Array[String]): Unit = {
    println("\n\ntestSquare()")
    testSquare()
    println("\n\ntestSquare2()")
    testSquare2()
    println("\n\ntestModulus()")
    testModulus()
    println("\n\ntestBiggestSquare()")
    testBiggestSquare()
    println("\n\ntestALoop()")
    testALoop()
    println("\n\ntestFindSum()")
    testFindSum()
    println("\n\ntestFib()")
    testFib()
    val args: Array[String] = Array("10", "200", "ten", "50")
    println(s"\n\nMilk.main(${args})")
    Milk.main(args)
  }
}
