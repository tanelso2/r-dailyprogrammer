(ns dp-335.core-test
  (:require [clojure.test :refer :all]
            [dp-335.core :refer :all]))

(deftest score-hand-test
  (testing "Scoring hands"
    (are [test-hand expected] (= expected (score-hand test-hand))
      [{:suit :clubs :value :A}
       {:suit :diamonds :value 6}
       {:suit :clubs :value 5}
       {:suit :clubs :value 10}
       {:suit :clubs :value 8}]
      4
      [{:suit :diamonds :value 5}
       {:suit :spades :value :Q}
       {:suit :clubs :value :J}
       {:suit :hearts :value :K}
       {:suit :clubs :value :A}]
      10
      [{:suit :clubs :value 8}
       {:suit :diamonds :value :A}
       {:suit :clubs :value 10}
       {:suit :hearts :value 6}
       {:suit :spades :value 7}]
      7)))

(deftest parse-card-str-test
  (testing "Parsing cards from strings"
    (are [test-str expected] (= expected (parse-card-str test-str))
      "AC" {:suit :clubs :value :A}
      "6D" {:suit :diamonds :value 6}
      "10C" {:suit :clubs :value 10}
      "9H" {:suit :hearts :value 9}
      "KS" {:suit :spades :value :K})))

(deftest score-hand-str-test
  (testing "Scoring hands from strings"
    (are [test-str expected] (= expected (score-hand-str test-str))
      "5D,QS,JC,KH,AC"   10
      "8C,AD,10C,6H,7S"  7
      "AC,6D,5C,10C,8C"  4
      "2C,3C,3D,4D,3S"   17
      "2C,3C,4D,4D,5S"   14
      "2H,2C,3S,4D,4S"   18
      "2H,2C,3S,4D,9S"   12
      "5H,5C,5S,JD,5D"   29
      "6D,JH,4H,7S,5H"   15
      "5C,4C,2C,6H,5H"   12
      "10C,8D,KS,8S,5H"  6
      "10C,5C,4C,7S,3H"  7
      "7D,3D,10H,5S,3H"  8
      "7C,KD,9D,8H,3H"   5
      "8S,AC,QH,2H,3H"   5
      "5H,5C,5S,JD,5D"   29)))
