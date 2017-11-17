(ns dp-339-intermediate.core-test
  (:require [clojure.test :refer :all]
            [dp-339-intermediate.core :refer :all]))

(deftest input-test
  (testing "The input from reddit"
    (let [input "10\n1 12 5 12 13 40 30 22 70 19\n23 10 10 29 25 66 35 33 100 65"]
      (is (= 5
             (count (get-most (parse-pairs input))))))))
