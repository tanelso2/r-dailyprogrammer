(ns dp-339-intermediate.core
  (:require [clojure.string :as str])
  (:import (java.lang Integer)))

(defn parse-int [x] (Integer/parseInt x))

(defn- parse-number-list
  [input-str]
  (map parse-int
    (str/split input-str #" ")))

(defn parse-pairs
  [input-str]
  (let [lines (str/split-lines input-str)
        num-pairs (parse-int (first lines))
        start-dates (parse-number-list (nth lines 1))
        end-dates (parse-number-list (nth lines 2))]
    (assert (= num-pairs (count start-dates) (count end-dates)))
    (map (comp sort vector) start-dates end-dates)))

(defn- in-between?
  [start end test-val]
  (and (>= test-val start)
       (<= test-val end)))

(defn- overlapping?
  [[start1 end1] [start2 end2]]
  (or (in-between? start1 end1 start2)
      (in-between? start1 end1 end2)
      (in-between? start2 end2 start1)
      (in-between? start2 end2 end1)))

(defn- remove-overlaps
  [pair full-list]
  (filter
    (partial (complement overlapping?) pair)
    full-list))

(defn days-reserved
  [pairs-list]
  (reduce +
          (for [[start end] pairs-list]
            (- end start))))

(defn- get-most-rec
  [constructed-list possible-pairs most-func]
  (if (empty? possible-pairs)
    constructed-list
    (apply max-key most-func
           (for [pair possible-pairs]
             (get-most-rec
               (conj constructed-list pair)
               (remove-overlaps pair possible-pairs)
               most-func)))))

(defn get-most-reservations
  [pairs]
  (get-most-rec [] pairs count))

(defn get-most-days-reserved
  [pairs]
  (get-most-rec [] pairs days-reserved))
