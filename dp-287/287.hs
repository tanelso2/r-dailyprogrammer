import Data.Digits
import Data.List
import Data.Maybe

largestDigit :: Int -> Int
largestDigit = maximum . digits 10

descDigits :: Int -> Int
descDigits n = unDigits 10 $ sortBy (flip compare) paddedNList
    where digitsN = digits 10 n
          lenDigitsN = length digitsN
          paddedNList = replicate (4 - lenDigitsN) 0 ++ digitsN

ascDigits :: Int -> Int
ascDigits = unDigits 10 . reverse . digits 10 . descDigits

kaprekar :: Int -> Maybe Int
kaprekar n
    | (length . group $ digitsN) > 1 = Just $ kaprekarHelper n
    | otherwise = Nothing
    where digitsN = digits 10 n
          kaprekarHelper 6174 = 0
          kaprekarHelper i
            | 6174 == result = 1
            | otherwise = 1 + kaprekarHelper result
            where result = descDigits i - ascDigits i

maximumIterations :: Int
maximumIterations = maximum $ mapMaybe kaprekar [0..9999]
