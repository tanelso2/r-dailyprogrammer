(ns dp-338-easy.core
  (:gen-class)
  (:require [clojure.string :as str])
  (:import (java.lang Integer)))

(defn parse-int [x] (Integer/parseInt x))

(defn floor [n] (int n))

(def days ["Saturday"
           "Sunday"
           "Monday"
           "Tuesday"
           "Wednesday"
           "Thursday"
           "Friday"])

(defn zellar
  [year month day]
  (let [[month year] (if (or (= month 1) (= month 2))
                      [(+ month 12) (- year 1)]
                      [month year])]
    (mod
      (+
        day
        (floor (/
                 (* 13
                    (+ month 1))
                 5))
        year
        (floor (/ year 4))
        (- (floor (/ year 100)))
        (floor (/ year 400)))
      7)))

(defn parse-year-month-day-str
  [s]
  (let [strs (str/split s #" ")]
    (assert (= 3 (count strs)))
    (mapv parse-int strs)))

(defn -main
  [date-str & args]
  (println (nth days (apply zellar (parse-year-month-day-str date-str)))))