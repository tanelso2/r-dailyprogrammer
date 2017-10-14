reverseFactorial :: Int -> Maybe Int
reverseFactorial n = helper n 1
    where helper num divider
           | remainder /= 0 = Nothing
           | result == 1 = Just divider
           | otherwise = helper result $ divider + 1
           where (result,remainder) = num `divMod` divider
