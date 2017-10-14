(ns dp-334.core
  (:require [clojure.string :as str])
  (:gen-class)
  (:import (java.lang Integer)))

(defn parse-int [x] (Integer/parseInt x))

(defn unflatten-transformation
  [transformation]
  (->> transformation
       (partition 3)))

(defn parse-input
  [input]
  (let [lines (str/split-lines input)
        number-strs (map #(str/split % #" ") lines)
        numbers (map #(map parse-int %) number-strs)
        [num-colors num-iterations] (first numbers)
        transformations (vec (map unflatten-transformation (rest numbers)))]
    (assert (= num-colors (count transformations)))
    {:num-colors num-colors
     :num-iterations num-iterations
     :transformations transformations}))

(def starting-state [[0]])

(defn do-iteration
  [config state]
  (let [transformations (:transformations config)
        unflattened-new-state (map (fn [x] (map #(nth transformations %) x)) state)] ; ((((int))))
      (->> unflattened-new-state
           (map #(apply map concat %)) ; (((int)))
           (apply concat)))) ; ((int))

(defn do-iterations
  [config starting-state]
  (let [current-state (atom starting-state)
        num-iterations (:num-iterations config)]
    (dotimes [_ num-iterations]
      (swap! current-state #(do-iteration config %)))
    @current-state))

(defn convert-to-pgm
  [config ending-state]
  (let [dimensions (count ending-state)
        state-lines (->> ending-state
                         (map #(str/join " " %))
                         (str/join "\n"))]
    (str/join "\n"
      ["P2"
       (str dimensions " " dimensions)
       (:num-colors config)
       state-lines])))

(defn -main
  [& args]
  (let [in (slurp *in*)
        config (parse-input in)
        ending-state (do-iterations config starting-state)]
    (assert (= (count ending-state) (count (first ending-state))) "Output should be a square")
    (println (convert-to-pgm config ending-state))))
