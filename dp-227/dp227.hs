import Data.List (takeWhile)
import Control.Applicative ((<$>))

spiralNumber :: (Int, Int) -> Int
spiralNumber (0, 0) = 1
spiralNumber (x, y) 
	| x == n && y > negN  	= firstNumber + (y - negN - 1)
	| y == n && x < n 		= firstNumber + numPerSegment + (n - x - 1)
	| x == negN && y < n 	= firstNumber + (2 * numPerSegment) + (n - y - 1)
	| y == negN && x > negN = firstNumber + (3 * numPerSegment) + (x - negN - 1)
	where
		n = max (abs x) (abs y)
		negN = negate n
		sqSize = 2 * n + 1
		firstNumber = (sqSize - 2) ^ 2 + 1
		numPerSegment = ((sqSize ^ 2) - ((sqSize - 2) ^ 2)) `div` 4

problemCoordsToCartesian :: Int -> (Int, Int) -> (Int, Int)
problemCoordsToCartesian size (a,b) = (a - midNum, midNum - b)
	where midNum = size `div` 2 + 1

outputSpiralNumber :: Int -> (Int, Int) -> Int
outputSpiralNumber size coords = spiralNumber $ problemCoordsToCartesian size coords

translateSpiralNumber :: Int -> (Int, Int)
translateSpiralNumber 1 = (0, 0)
translateSpiralNumber s
	| segment == 0 = (n, s - firstNumber - n + 1)
	| segment == 1 = (firstNumber + numPerSegment - s + n - 1, n)
	| segment == 2 = (negN, firstNumber + (2 * numPerSegment) - s + n - 1)
	| segment == 3 = (s - firstNumber - (3 * numPerSegment) - n + 1, negN)
	where
		innerSquares = takeWhile (\x -> x ^ 2 < s) [1,3..]
		sqSize = 2 + (innerSquares !! (length innerSquares - 1))
		numPerSegment = ((sqSize ^ 2) - ((sqSize - 2) ^ 2)) `div` 4
		firstNumber = (sqSize - 2) ^ 2 + 1
		n = (sqSize - 1) `div` 2
		negN = negate n
		segment = (s - firstNumber) `div` numPerSegment

cartesianToProblemCoords :: Int -> (Int, Int) -> (Int, Int)
cartesianToProblemCoords size (x, y) = (x + midNum, midNum - y)
	where midNum = size `div` 2 + 1

outputLocation :: Int -> Int -> (Int, Int)
outputLocation size s = cartesianToProblemCoords size $ translateSpiralNumber s

main = do
	gridSize <- read <$> getLine
	numbers <- (map read) . words <$> getLine
	let l = length numbers
	if l == 1 then print $ outputLocation gridSize $ numbers !! 0
	else if l == 2 then print $ outputSpiralNumber gridSize (numbers !! 0, numbers !! 1)
	else error "Your input is way off"
