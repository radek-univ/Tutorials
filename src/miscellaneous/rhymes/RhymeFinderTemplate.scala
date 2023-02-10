package tutorials
package miscellaneous.rhymes

import scala.collection.immutable.HashMap

object RhymeFinderTemplate {
  val dictionaryFileName = "enter_the_file_name_here.txt"
  val poeticWorkFileName = "enter_the_second_file_name_here.txt"

  val dictionary: HashMap[String, String] = loadPronouncationDictionary()

  def loadPronouncationDictionary(): HashMap[String, String] = {
    val lines = getLines(dictionaryFileName)
    // TODO read the contents into a dictionary somehow
    // For the following lines of the input
    // "BANANA  B AH0 N AE1 N AH0"
    // "TEA  T IY1"
    // return something like this:
    val arrayOfPairs = Array(("BANANA", "B AH N AE N AH"), ("TEA", "T IY"))
    HashMap.from(arrayOfPairs)
  }

  def loadPoeticWork(): Array[String] = {
    val lines = getLines(poeticWorkFileName)
    // TODO implement some filtering
    // Find a way to get rid of lines which are not useful.
    lines
  }

  def normalise(s: String): String = {
    // TODO normalise s
    // For example:
    // Input: "  A   *sentence*   that—although interesting-looking—has      too \"exaggerated\" a punctuation;    at least I think so."
    // Output: "A SENTENCE THAT ALTHOUGH INTERESTING LOOKING HAS TOO EXAGGERATED A PUNCTUATION AT LEAST I THINK SO"
    s
  }

  def toPhonemes(s: String): String = {
    // TODO Replace words of s with the appropriate dictionary entry, if it exists
    // dictionary.getOrElse(word, word)
    // if not, fall back to just word.
    // For example:
    // Input: "BANANA TEA MATROID"
    // Output: "B AH N AE N AH T IY MATROID"
    s
  }

  def similarity(str1: String, str2: String): Int = {
    // TODO Calculate the length of common suffix
    0
  }

  def findBestRhyme(prompt: String, poeticWorkLines: Array[String]): Int = {
    val normalisedPrompt = toPhonemes(normalise(prompt))
    var (bestSimilarity, bestIndex, bestLine) = (0, 0, "Nothing found so far.")
    var i = 0
    while (i < poeticWorkLines.length) {
      val line = toPhonemes(normalise(poeticWorkLines(i)))
      val score = similarity(normalisedPrompt, line)
      // TODO if score is better than last, update best
      i += 1
    }
    bestIndex
  }

  def main(args: Array[String]): Unit = {
    val poeticWorkLines = loadPoeticWork()
    val prompt = args.mkString(" ")

    println(s"Finding rhyme for `$prompt`.")

    val indexOfBestLine = findBestRhyme(prompt, poeticWorkLines)

    println("\n\nA POEM:")
    println(prompt)
    for (i <- ((indexOfBestLine - 2) max 0) to indexOfBestLine)
      println(poeticWorkLines(i))
  }

  def getLines(fileName: String): Array[String] = {
    import java.nio.charset.{Charset, CodingErrorAction}
    import scala.io.Source
    import scala.util.{Failure, Success, Using}

    val decoder = Charset.forName("UTF-8").newDecoder()
    decoder.onMalformedInput(CodingErrorAction.IGNORE)
    Using(Source.fromFile(fileName)(decoder))(_.getLines().toArray) match {
      case Success(lines) => lines
      case Failure(e) =>
        println(s"Error when trying to open the file `$fileName`.")
        println(s"Please make sure it is in the same directory in which you are running your program.")
        println(e.toString)
        Array()
    }
  }
}
