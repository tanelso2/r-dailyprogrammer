isVowel :: Char -> Bool
isVowel letter = letter `elem` vowels
	where vowels = ['a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U']

isConsonant :: Char -> Bool
isConsonant letter = letter `elem` ['A'..'z'] && not (isVowel letter)

disemVowel :: String -> (String, String)
disemVowel s = (filter isConsonant s, filter isVowel s)
