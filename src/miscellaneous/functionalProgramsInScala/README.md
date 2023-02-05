Below you will find three (simple to moderately difficult) tasks and their example solutions in both Scala and Haskell.
The aim was to show that it is possible to program functionally in Scala.

If you wish, you can either:

- read the tasks below, try to solve them yourself, and then compare them with the example solutions, or
- read the example solutions right away, or
- completely ignore this catalogue, as it is not directly related to our subject and is provided as a curiosity.

On the other hand, a useful exercise would be to consider whether you can solve these tasks imperatively in Scala.
Especially the third one may prove challenging, because it uses a custom data structure.

-----

### Exercise 1

Write a function `onceInARow` of type `List[Int] -> List[Int]` which, given a list of integers, selects elements which
occur there exactly once in a row.

**Example**

```scala
onceInARow(List(1,1,3,5,5,5,3,5)) == List(3,3,5)
```

### Exercise 2
A list is **monotone** whenever it is nonincreasing or nondecreasing.

Write a function `monotoneFragments` which, given a list of integers,
determines the minimum number of contiguous fragments into which the list must be divided,
so that each fragment is monotone. (For an empty list, the correct output is zero).

**Example**

```scala
monotoneFramgnets(List(2, 3, 3, 5, 0, -1, -7, 1, 3, 5, 5, 2, 4, 6)) == 4
// The list can be divided into [2, 3, 3], [5, 0, -1, -7], [1, 3, 5, 5], [2, 4, 6].
   ```
### Exercise 3

The type `ChristmasTree` is defined in Haskell as:
```haskell
data ChristmasTree
     = Tip
     | Twig ChristmasTree
     | Branching ChristmasTree ChristmasTree
```
and similarly in Scala as:
```scala
  sealed trait ChristmasTree
  case class Tip() extends ChristmasTree
  case class Twig(subtree: ChristmasTree) extends ChristmasTree
  case class Branching(subtree1: ChristmasTree, subtree2: ChristmasTree) extends ChristmasTree
  ```

Candles are to be put at the nodes of the tree.
We say a tree is **well illuminated** if all the edges have candles on at least one end.
Write a function `wellIlluminated` which, given a tree, computes the minimal number of candles required to make it well illuminated.

**Example**
```
█═══█
    ║
    █═══█
        ║
        █
```
Above tree requires two candles:
```
█═══🕯
    ║
    █═══🕯
        ║
        █
```


------
You can run the example code here:

**Haskell**
[https://replit.com/@radekpi/FunctionalProgramsInHaskell#Main.hs](https://replit.com/@radekpi/FunctionalProgramsInHaskell#Main.hs)

****

