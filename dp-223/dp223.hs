import Control.Applicative
import System.IO
import System.IO.Unsafe

problemWord :: String -> String -> Bool
problemWord answer badWord = badWord == filter (`elem` badWord) answer

problemCount :: [String] -> String -> Int
problemCount wordList badWord = length $ problemWords wordList badWord

problemWords :: [String] -> String -> [String]
problemWords wordList badWord = filter (\x -> problemWord x badWord) wordList

main = do
	wordList <- lines <$> readFile "enable1.txt"
	mapM_ print $ problemWords wordList "shit"

problemCountUnsafe :: String -> Int
problemCountUnsafe badWord = length $ problemWordsUnsafe badWord

problemWordsUnsafe :: String -> [String]
problemWordsUnsafe badWord = filter (\x -> problemWord x badWord) words
	where words = lines . unsafePerformIO . readFile $ "enable1.txt"
