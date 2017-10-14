(ns dp-335.core-test
  (:require [clojure.test :refer :all]
            [dp-335.core :refer :all]))

(deftest first-test
  (testing "5D, QS, JC, KH, AC"
    (let [hand [{:suit :diamonds :value 5}
                {:suit :spades :value :Q}
                {:suit :clubs :value :J}
                {:suit :hearts :value :K}
                {:suit :clubs :value :A}]
          expected 10
          result (score-hand hand)]
      (is (= expected result)))))

(deftest second-test
  (testing "8C, AD, 10C, 6H, 7S"
    (let [hand [{:suit :clubs :value 8}
                {:suit :diamonds :value :A}
                {:suit :clubs :value 10}
                {:suit :hearts :value 6}
                {:suit :spades :value 7}]
          expected 7
          result (score-hand hand)]
      (is (= expected result)))))

(deftest third-test
  (testing "AC, 6D, 5C, 10C, 8C"
    (let [hand [{:suit :clubs :value :A}
                {:suit :diamonds :value 6}
                {:suit :clubs :value 5}
                {:suit :clubs :value 10}
                {:suit :clubs :value 8}]
          expected 4
          result (score-hand hand)]
      (is (= expected result)))))
