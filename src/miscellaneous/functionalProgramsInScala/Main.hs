module Main where

import Control.Monad (forM_, guard)

-- This file contains sample solutions to the three additional exercises.

------------------------------------------------------------
-- Exercise 1
------------------------------------------------------------

-- Solution 1.1
-- Use foldl; while reading the list, add the element
-- once certain it is different than its neighbours.
onceInARow1 :: [Int] -> [Int]
onceInARow1 [] = []
onceInARow1 (h : t) =
  let f (prev, isSingle, res) curr
        | prev == curr = (curr, False, res)
        | isSingle = (curr, True, prev : res)
        | otherwise = (curr, True, res)
      (final, isSingle, res) = foldl f (h, True, []) t
   in reverse $ if isSingle then final : res else res

-- Solution 1.2
-- Extend the list with two empty "guards" (Nothing)
onceInARow2 :: [Int] -> [Int]
onceInARow2 list =
  let list' = [Nothing] ++ map Just list ++ [Nothing]
   in [b | (a, b, c) <- zip3 list' list (tail $ tail list'), a /= Just b && c /= Just b]

-- Solution 1.3
-- Same approach as 1.2, but use list monad instead of list comprehension.
onceInARow3 :: [Int] -> [Int]
onceInARow3 list = do
  let list' = [Nothing] ++ map Just list ++ [Nothing]
  (a, b, c) <- zip3 list' list $ tail $ tail list'
  guard $ a /= Just b && c /= Just b
  return b

------------------------------------------------------------
-- Exercise 2
------------------------------------------------------------

-- Solution 2.1
-- Use foldl; while reading the list, remember the current trend, increment the result when it changes.
data Monotoncity = Constant | Nonincreasing | Nondecreasing

monotoneFragments :: [Int] -> Int
monotoneFragments list = case list of
  [] -> 0
  h : t -> res
    where
      f (Constant, n, res) m
        | n == m = (Constant, m, res)
        | n < m = (Nondecreasing, m, res)
        | n > m = (Nonincreasing, m, res)
      f (Nondecreasing, n, res) m
        | n > m = (Constant, m, res + 1)
      f (Nonincreasing, n, res) m
        | n < m = (Constant, m, res + 1)
      f (direction, _, res) m = (direction, m, res)
      (_, _, res) = foldl f (Constant, h, 1) t

------------------------------------------------------------
-- Exercise 3.
------------------------------------------------------------

data ChristmasTree
  = Tip
  | Twig ChristmasTree
  | Branching ChristmasTree ChristmasTree
  deriving (Eq, Show)
  
-- Solution 3.1
-- Compute the number recursively, using the results for child nodes. 
-- For each subtree, compute the lowest number needed, assuming that a candle is to be placed in its root or not.
wellIlluminated :: ChristmasTree -> Int
wellIlluminated tree =
  let (with, without) = wellIlluminated' tree
   in min with without
  where
    wellIlluminated' :: ChristmasTree -> (Int, Int)
    wellIlluminated' Tip = (1, 0)
    wellIlluminated' (Twig subtree) =
      let (with, without) = wellIlluminated' subtree
       in (1 + min with without, with)
    wellIlluminated' (Branching subtree1 subtree2) =
      let (with1, without1) = wellIlluminated' subtree1
          (with2, without2) = wellIlluminated' subtree2
       in (1 + min with1 without1 + min with2 without2, with1 + with2)





------------------------------------------------------------
-- Additional code to test the solutions above
------------------------------------------------------------
main :: IO ()
main = do
  putStrLn "Which test would you like to run?"
  putStrLn "Give a number from 1 to 3 and press <Enter>: "
  ch <- getChar
  case ch of
    '1' -> testsForExercise1
    '2' -> testsForExercise2
    '3' -> testsForExercise3
    _ -> do
      putStrLn "Invalid input, try again:"
      main
      
-- sample lists to test exercises 1 and 2 
lists =
  [ [1, 2, 2, 3, 4, 4, 5],
    [1, 2, 3, 2, 3, 4, 5, 6, 5, 6, 7],
    []
  ]

------------------------------------------------------------
testsForExercise1 :: IO ()
testsForExercise1 =
  forM_
    lists
    ( \list -> do
        putStrLn $ "----\nTesting list " ++ show list
        putStrLn $ "> answer 1: " ++ show (onceInARow1 list)
        putStrLn $ "> answer 2: " ++ show (onceInARow2 list)
        putStrLn $ "> answer 3: " ++ show (onceInARow3 list)
    )

------------------------------------------------------------
testsForExercise2 :: IO ()
testsForExercise2 =
  forM_
    lists
    ( \list -> do
        putStrLn $ "----\nTesting list " ++ show list
        putStrLn $ "> answer: " ++ show (monotoneFragments list)
    )

------------------------------------------------------------
testsForExercise3 :: IO ()
testsForExercise3 =
  forM_
    trees
    ( \tree -> do
        putStrLn $ unlines $ "----" : draw tree True
        let answer = wellIlluminated tree
        putStrLn $ "Above tree needs " ++ show answer ++ " candle" ++ (if answer == 1 then "" else "s") ++ " to be well-lit.\nLook at it carefully and make sure this is the correct answer."
    )

-- our most elaborate example of a tree
sampleTree :: ChristmasTree
sampleTree =
  Branching
    ( Branching
        (Twig (Branching Tip (Twig Tip)))
        (Branching (Twig (Twig Tip)) Tip)
    )
    ( Branching
        (Twig (Twig (Twig (Branching Tip Tip))))
        (Twig (Branching Tip (Twig Tip)))
    )

-- list of sample trees
trees :: [ChristmasTree]
trees = [Tip, Twig Tip, Twig $ Twig $ Twig $ Twig Tip, sampleTree]

-- an auxiliary tree-drawing function
draw :: ChristmasTree -> Bool -> [String]
draw Tip _ = ["█"]
draw (Twig subtree) True =
  zipWith (++) ("█═══" : repeat "    ") (draw subtree False)
draw (Twig subtree) False =
  ["█", "║"] ++ draw subtree True
draw (Branching subtree1 subtree2) _ =
  zipWith (++) ("█═══" : repeat "║   ") (draw subtree2 True ++ [""]) ++ draw subtree1 True