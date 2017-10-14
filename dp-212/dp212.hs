import qualified Data.Char (toLower)

lowerCaseLetters = ['a'..'z']
isLowerCase :: Char -> Bool
isLowerCase letter = letter `elem` lowerCaseLetters

capitalLetters = ['A'..'Z']
isCapital :: Char -> Bool
isCapital letter = letter `elem` capitalLetters

vowels = ['a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U']
isVowel :: Char -> Bool
isVowel letter = letter `elem` vowels

letterTranslate :: Char -> String
letterTranslate letter
	| isVowel letter = [letter]
	| isCapital letter = [letter, 'o', Data.Char.toLower letter]
	| isLowerCase letter = [letter, 'o', letter]
	| otherwise = [letter]

stringTranslate :: String -> String
stringTranslate = foldr (\x acc -> letterTranslate x ++ acc) ""
--stringTranslate [] = []
--stringTranslate (x:xs) = letterTranslate x ++ stringTranslate xs

letters = ['A'..'z']
isLetter :: Char -> Bool
isLetter letter = letter `elem` letters

decodeString :: String -> String
decodeString [] = []
decodeString (x:xs) = x : if isLetter x && not (isVowel x)
							then decodeString(drop 2 xs)
							else decodeString xs
