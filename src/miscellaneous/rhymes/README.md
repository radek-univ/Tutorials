## Challenge 1 – Rhymes

Can computers create poems automatically? It is hard to tell, we'd better ask ChatGPT what does it think on that matter... :)
However, with a little help from Shakespeare, even our programs will be able to rhyme.

## The challenge

> Write a Scala program which, given an input String, searches a body of text and outputs the line that best rhymes with it.

It is not a straightforward task, it may be, well..., challenging to you, but I believe it is within your reach, and the result should be fun to use :)

I have provided you with a solution template in the file `RhymeFinderTemplate.scala`, but there are some problems you need to solve.

---------

## How should the solution work?

### Step 1 – loading a dictionary
Finding rhymes is not obvious in English.
Fortunately, there are some pronunciation dictionaries available online, like [this one](https://github.com/Alexir/CMUdict) (more info [here](http://www.speech.cs.cmu.edu/cgi-bin/cmudict)).
For finding rhymes, the file `cmudict-0.7b` will be suitable.

In the solution template, there is a Scala function `getLines(filename: String): Array[String]` which can open a file and put all its contents into `Array[String]`, one line per array position:
```scala
val dictLines = getLines("cmudict-0.7b")
```
But further processing is needed.

**Filtering**   
We are only interested in lines starting with a letter. 
A quick filtering does what we need:
```scala
val dictLines2 = dictLines.filter(line => line.nonEmpty && line(0).isLetter)
```
but that is only the start.
For example, the dictionary file contains a line
```
PROGRAMMING  P R OW1 G R AE2 M IH0 NG
```
We would like to store is as an item of an Array of pairs, ignoring numbers `0 1 2` which denote _lexical stress_.
```
val dictionaryList = Array(..., ("PROGRAMMING", "P R OW G R AE M IH NG"), ...)
```
or a map (for better lookup time)
```scala
import scala.collection.immutable
val dictionary = immutable.HashMap.from(dictionaryList)
assert(dictionary("PROGRAMMING") === "P R OW G R AE M IH NG")
```

### Step 2 – Normalising input Strings
Before we can use the dictionary, we need a method to 'normalise' input text, getting rid of extra spaces, commas:
```scala
val input = "  A   *sentence*   that—although interesting-looking—has      too \"exaggerated\" a punctuation;    at least I think so."
val output = "A SENTENCE THAT ALTHOUGH INTERESTING LOOKING HAS TOO EXAGGERATED A PUNCTUATION"
assert(normalise(input) === output)
```
To implement such normalisation, you may need methods like:
- `.toUpperCase()`,
- `.map(ch => if (...) ... else ...)`
- `.split(...)`
- `.filter(...)`
- `.mkString(...)`
- `ch.isLetter`
- `ch.isSpaceChar`
- and maybe others from [String](https://www.scala-lang.org/api/2.13.x/scala/collection/StringOps.html) or [Array](https://www.scala-lang.org/api/2.13.x/scala/Array.html) class.

### Step 3 – Text to phonemes
When we have our inputs normalised, we may now use the dictionary to produce strings of phonemes. For example, given a string consisting of 4 words:
```
"(WORD 1) (WORD 2) (WORD 3) (WORD 4)"
```
we would like to produce:
```
"(PHONEMES OF WORD 1) (PHONEMES OF WORD 2) (PHONEMES OF WORD 3) (PHONEMES OF WORD 4)"
```
Remember about methods listed above! :)

### Step 4 – Comparing strings of phonemes
It is easy to identify two strings of phonemes which rhyme well.
Just read them from the end and see how many characters match – the more, the better.
You need to implement function `similarity(line1, line2)` outputting that similarity score. For example:
```
similarity("AB CD EF G V WX YZ", "H IJ KL V WX YZ") === 8
```

### Step 5 – Reading poetry and finding rhymes
We can load arbitrary text. 
Googling `all works of shakespeare txt` lets you find a [file](https://ocw.mit.edu/ans7870/6/6.006/s08/lecturenotes/files/t8.shakespeare.txt) (with a licence for personal use) containing all works of Shakespeare.
It is good to make sure we only work with lines we want. Perhaps a `.filter(...)` could be used before further processing.

Now, the task is easy:
- Read the prompt from the command line,
- normalise it,
- and transform it to a string S of phonemes.
- Then for each line of the poetic work of your choice:
  - normalise it,
  - transform it to a string T of phonemes,
  - compute similarity of S and T
    - if it is bigger than what was attained before, store it as the current best.
- At the very end, you will have found the best rhyme.

**Good luck!**
If you manage to implement this program, you may share it with me if you want.

