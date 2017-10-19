(ns dp-335.core
  (:gen-class)
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str])
  (:import (java.lang Integer)))

(defn parse-int [x] (Integer/parseInt x))

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
    (->> hand
         (group-by :value)
         (map #(count (nth % 1)))
         (reduce
           (fn [accum x]
              (+ accum (get points-map x 0)))
           0))))

(defn- get-flushes-score
  [hand]
  (let [suits (map :suit hand)]
    (cond
      (apply = suits) 5
      (apply = (drop-last suits)) 4
      :else 0)))

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
         (map (partial map get-value-from-card))
         (map (partial reduce + 0))
         (filter (partial = 15))
         (count)
         (* 2))))

(defn- sequential-run?
  [cards]
  (let [sorted-values (sort (map get-order-from-card cards))]
    (every? identity
      (map #(= (- %2 1) %1)
           sorted-values
           (rest sorted-values)))))

(defn- get-runs-score
  [hand]
  (let [points-map {3 3
                    4 4
                    5 5}]
    (->> (combo/subsets hand)
         (filter sequential-run?)
         (map count)
         (map #(get points-map % 0))
         (apply +))))

(defn score-hand
  [hand]
  (reduce +
          0
          (map #(% hand) [get-fifteens-score
                          get-pairs-score
                          get-flushes-score
                          get-nobs-score
                          get-runs-score])))

(defn- parse-suit-str
  [s]
  (case s
    "D" :diamonds
    "C" :clubs
    "H" :hearts
    "S" :spades))

(defn- parse-value-str
  [s]
  (case s
    ("A" "J" "Q" "K") (keyword s)
    (parse-int s)))

(defn parse-card-str
  [card-str]
  (let [trimmed-card-str (str/trim card-str)
        split-point (- (count trimmed-card-str) 1)
        [card-value-str card-suit-str] (map
                                         (partial apply str)
                                         (split-at split-point trimmed-card-str))]
    {:suit (parse-suit-str card-suit-str)
     :value (parse-value-str card-value-str)}))

(defn parse-hand-str
  [hand-str]
  (mapv parse-card-str
        (str/split (str/trim hand-str) #",")))

(defn score-hand-str
  [hand-str]
  (score-hand (parse-hand-str hand-str)))
