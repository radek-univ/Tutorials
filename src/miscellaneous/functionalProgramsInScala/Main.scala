package tutorials
package miscellaneous.functionalProgramsInScala

import scala.annotation.tailrec
import scala.io.StdIn.readChar

object Main {

  // Exercise 1
  // Sample solution 1.1
  // Using a fold left.
  private def onceInARow1(list: List[Int]): List[Int] = {
    def f(acc: (Int, Boolean, List[Int]), current: Int): (Int, Boolean, List[Int]) = {
      val (previous, single, result) = acc // We unpack the tuple with a destructuring assignment.
      if (previous == current)
        (current, false, result)
      else if (single)
        (current, true, previous :: result)
      else
        (current, true, result)
    }

    list match {
      case Nil => List()
      case h :: t =>
        val (last, single, result) = t.foldLeft(
          (h, true, List[Int]())
        )(f)
        (if (single) last :: result else result).reverse
    }
  }

  // Solution 1.2
  // This solution uses Scala's "for {...} yield (...)" notation,
  // which is similar to Haskell's list comprehensions and "do" notation.
  // Unfortunately, there's no zip3 in Scala's standard library.
  private def onceInARow2(list: List[Int]): List[Int] = {
    val list2 = List(None) ++ list.map(Some(_)) ++ List(None)
    for {
      ((a, b), c) <- list2 zip list zip list2.tail.tail
      if !(a contains b) && !(c contains b)
    } yield b
  }

  // Exercise 2

  // Solution 2.1
  object M extends Enumeration {
    type T = Value
    val Constant, Nondecreasing, Nonincreasing = Value
  }

  private def monotoneFragments(list: List[Int]) = {
    def f(t: (M.T, Int, Int), m: Int) = t match {
      case (M.Constant, n, res) =>
        if (n == m) (M.Constant, m, res)
        else if (n < m) (M.Nondecreasing, m, res)
        else (M.Nonincreasing, m, res)
      case (M.Nondecreasing, n, res) if n > m => (M.Constant, m, res + 1)
      case (M.Nonincreasing, n, res) if n < m => (M.Constant, m, res + 1)
      case (direction, _, res) => (direction, m, res)
    }

    list match {
      case Nil => 0
      case h :: t => t.foldLeft((M.Constant: M.T, h, 1))(f)._3
    }
  }


  // Exercise 3
  sealed trait ChristmasTree

  case class Tip() extends ChristmasTree

  case class Twig(subtree: ChristmasTree) extends ChristmasTree

  case class Branching(subtree1: ChristmasTree, subtree2: ChristmasTree) extends ChristmasTree

  // Solution 3.1
  def wellIlluminated(tree: ChristmasTree): Int = {
    val (withCandle, withoutCandle) = wellIlluminatedHelper(tree)
    withCandle min withoutCandle
  }

  def wellIlluminatedHelper(tree: ChristmasTree): (Int, Int) = tree match {
    case Tip() => (1, 0)
    case Twig(subtree) =>
      val (withCandle, withoutCandle) = wellIlluminatedHelper(subtree)
      (1 + (withCandle min withoutCandle), withCandle)
    case Branching(subtree1, subtree2) =>
      val (withCandle1, withoutCandle1) = wellIlluminatedHelper(subtree1)
      val (withCandle2, withoutCandle2) = wellIlluminatedHelper(subtree2)
      (1 + (withCandle1 min withoutCandle1) + (withCandle2 min withoutCandle2), withCandle1 + withCandle2)
  }

  // Additional code

  val lists: List[List[Int]] = List(List(1, 2, 2, 3, 4, 4, 5), List(1, 2, 3, 2, 3, 4, 5, 6, 5, 6, 7), List())

  // ------------------------------------------------------------
  def testsForExercise1(): Unit = {
    for (list <- lists) {
      println(s"----\nTesting list $list")
      println(s"> answer 1: ${onceInARow1(list)}")
      println(s"> answer 2: ${onceInARow2(list)}")
    }
  }

  // ------------------------------------------------------------
  def testsForExercise2(): Unit = {
    for (list <- lists) {
      println(s"----\nTesting list $list")
      println(s"> answer: ${monotoneFragments(list)}")
    }
  }

  def testsForExercise3(): Unit = {
    for (tree <- trees) {
      println(("----" :: draw(tree, horizontal = true)).mkString("\n"))
      val answer = wellIlluminated(tree)
      println(s"Above tree needs $answer candle${if (answer == 1) "" else "s"} to be well-lit.\nLook at it carefully and make sure this is the correct answer.")
    }
  }

  val sampleTree: ChristmasTree = Branching(
    Branching(
      Twig(Branching(Tip(), Twig(Tip()))),
      Branching(Twig(Twig(Tip())), Tip())
    ), Branching(
      Twig(Twig(Twig(Branching(Tip(), Tip())))),
      Twig(Branching(Tip(), Twig(Tip())))
    )
  )

  val trees: List[ChristmasTree] = List(Tip(), Twig(Tip()), Twig(Twig(Twig(Twig(Tip())))), sampleTree)

  def draw(tree: ChristmasTree, horizontal: Boolean): List[String] = tree match {
    case Tip() => List("█")
    case Twig(subtree) if horizontal =>
      def prefix: LazyList[String] = LazyList("    ") #::: prefix

      ("█═══" #:: prefix).zip(draw(subtree, horizontal = false)).toList.map { case (a, b) => a ++ b }
    case Twig(subtree) => List("█", "║") ++ draw(subtree, horizontal = true)
    case Branching(subtree1, subtree2) =>
      def prefix: LazyList[String] = LazyList("║   ") #::: prefix

      ("█═══" #:: prefix).zip(draw(subtree2, horizontal = true) ++ List("")).toList.map { case (a, b) => a ++ b } ++ draw(subtree1, horizontal = true)
  }

  @tailrec
  def main(args: Array[String]): Unit = {
    println("Which test would you like to run?")
    println("Give a number from 1 to 3 and press <Enter>: ")
    val ch = readChar()
    ch match {
      case '1' => testsForExercise1()
      case '2' => testsForExercise2()
      case '3' => testsForExercise3()
      case _ =>
        println("Invalid input, try again:")
        main(args)
    }
  }
}