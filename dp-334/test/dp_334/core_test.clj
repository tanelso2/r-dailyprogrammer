(ns dp-334.core-test
  (:require [clojure.test :refer :all]
            [dp-334.core :refer :all]))

(deftest do-iteration-test
  (let [config {:transformations [[[0 0 0]
                                   [0 1 0]
                                   [0 0 0]]
                                  [[1 1 1]
                                   [1 1 1]
                                   [1 1 1]]]}]
    (testing "One iteration"
      (let [expected-value [[0 0 0]
                            [0 1 0]
                            [0 0 0]]]
        (is (= expected-value (do-iteration config starting-state)))))
    (testing "Two iterations"
      (let [expected-value [[0 0 0 0 0 0 0 0 0]
                            [0 1 0 0 1 0 0 1 0]
                            [0 0 0 0 0 0 0 0 0]
                            [0 0 0 1 1 1 0 0 0]
                            [0 1 0 1 1 1 0 1 0]
                            [0 0 0 1 1 1 0 0 0]
                            [0 0 0 0 0 0 0 0 0]
                            [0 1 0 0 1 0 0 1 0]
                            [0 0 0 0 0 0 0 0 0]]]
        (is (= expected-value (do-iterations
                                (assoc config :num-iterations 2)
                                starting-state)))))))
