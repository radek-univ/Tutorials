package tutorials
package tutorial2

// If you prove that this loop stops for every starting n, you will get very famous! :)
// https://en.wikipedia.org/wiki/Collatz_conjecture
object Collatz {
  def main(args: Array[String]): Unit = {
    var n = BigInt(args(0))
    while (n > 1) {
      if (n % 2 == 0)
        n /= 2
      else
        n = 3*n + 1
    }
    println("DONE")
  }
}
