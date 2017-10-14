(ns dp-335.core
  (:gen-class)
  (:require [clojure.math.combinatorics :as combo]))

(defn- get-value-from-card
  [card]
  (let [value (:value card)]
    (case value
      (:J :Q :K) 10
      :A 1
      value)))

(defn- get-order-from-card
  [card]
  (let [value (:value card)]
    (case value
      :J 11
      :Q 12
      :K 13
      :A 1
      value)))

(defn- get-pairs-score
  [hand]
  (let [points-map {2 2
                    3 6
                    4 12}]
    (reduce (fn [accum x]
              (+ accum (get points-map x 0)))
            0
            (map
              #(count (nth % 1))
              (group-by :value hand)))))

(defn- get-flushes-score
  [hand]
  (let [suits (map :suit hand)]
    (if (apply = suits)
      5
      (if (apply = (drop-last suits))
          4
          0))))

(defn- get-nobs-score
  [hand]
  (let [nob-suit (:suit (last hand))]
    (if (some
          #(and (= nob-suit (:suit %))
                (= :J (:value %)))
          (drop-last hand))
        1
        0)))

(defn- get-fifteens-score
  [hand]
  (let [subsets (combo/subsets hand)]
    (->> subsets
         (filter #(= 15
                     (reduce + 0 (map get-value-from-card %))))
         (count)
         (* 2))))

(defn- get-runs-score
  [hand]
  (let [points-map {3 3
                    4 4
                    5 5}
        sorted-values (sort (map get-order-from-card hand))]
    (->> (map #(= (- %2 1) %1)
           sorted-values
           (rest sorted-values))
         (partition-by identity)
         (filter first)
         (map count)
         (apply max)
         (+ 1)
         (#(get points-map % 0)))))

(defn score-hand
  [hand]
  (reduce +
          0
          (map #(% hand) [get-fifteens-score
                               get-pairs-score
                               get-flushes-score
                               get-nobs-score
                               get-runs-score])))

