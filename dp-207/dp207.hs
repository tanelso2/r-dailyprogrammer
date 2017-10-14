translateDNA :: Char -> Char
translateDNA 'A' = 'T'
translateDNA 'C' = 'G'
translateDNA 'G' = 'C'
translateDNA 'T' = 'A'
translateDNA _ = error "Not a valid DNA character"

translateDNAStrand :: String -> String
translateDNAStrand = map translateDNA
