import Data.List

lowercase = ['a'..'z']
uppercase = ['A'..'Z']

atbash :: Char -> Char
atbash letter 
	| letter `elem` lowercase = atbash_help lowercase
	| letter `elem` uppercase = atbash_help uppercase
	| otherwise = letter
	where
		findIndex caseAlphabet = (case (elemIndex letter caseAlphabet) of 
										Just i -> i
										Nothing -> 0)
		atbash_help caseAlphabet = (reverse caseAlphabet) !! (findIndex caseAlphabet)
		 
main = do
		putStrLn "What do you want atbashed?"
		line <- getLine
		putStrLn (map atbash line)
