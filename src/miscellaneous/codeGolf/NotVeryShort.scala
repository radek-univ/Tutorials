package tutorials
package miscellaneous.codeGolf

object NotVeryShort {
  def referenceSolution(n: Int): String = {
    var canvas = Array.fill(n * 4)(Array.fill(n * 4)(' ')) // empty 2D array of size 4n x 4n containing ` `:Char
    var pattern1 = Array(
      " /\\ ",
      "/  \\",
      "\\  /",
      " \\/ "
    ).map(_.toArray)
    var pattern2 = Array(
      " /\\ ",
      "//\\\\",
      "\\\\//",
      " \\/ "
    ).map(_.toArray)

    // Writes the pattern on the canvas; the upper left corner is aligned at the position (x0, y0)
    def imprint(pattern: Array[Array[Char]], canvas: Array[Array[Char]], x0: Int, y0: Int): Unit = {
      var dy = 0
      while (dy < pattern.length) {
        var dx = 0
        while (dx < pattern(dy).length) {
          val character = pattern(dy)(dx)
          if (character != ' ') // If the pattern has a space at that position, we do not place it on the canvas.
            canvas(y0 + dy)(x0 + dx) = character
          dx += 1
        }
        dy += 1
      }
    }

    // Paint the whole chessboard onto the canvas
    var j = 0
    while (j < n) {
      var i = 0
      while (i < n) {
        val (x0, y0) = (2 * n - 2 + 2 * i - 2 * j, 2 * i + 2 * j)
        // The above expression was invented as follows:
        // the topmost field has its top left corner at the position (2*n-2, 0)
        // Adding one to i should move the pattern 2 positions to the right and 2 positions down.
        // Adding one to j should move the pattern 2 positions to the left and 2 positions down.
        val pattern = if ((i + j) % 2 == 1) pattern1 else pattern2
        // We choose patterns depending on the parity of the sum i + j
        imprint(pattern, canvas, x0, y0)
        i += 1
      }
      j += 1
    }

    // Transform the array of arrays of Chars to an ordinary String.
    // Join elements of rows without putting anything in between their elements (`_.mkString("")`), and
    // join the rows with a newline character:
    canvas.map(_.mkString("")).mkString("\n")
  }

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10)
      println(referenceSolution(i))
  }
}
