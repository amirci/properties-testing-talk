(ns count-floors.core)

(defn walk-one
  [f i]
  (+ f (if (= i \() 1 -1)))

(defn count-total
  [s]
  (reduce walk-one 0 s))

(defn to-basement
  [[floor pos] i]
  [(walk-one floor i) (inc pos)])

(def not-basement (comp (partial not= -1) first))

(def zero-floor [0 0])

(def char-position last)

(defn find-basement
  [s]
  (->> s
       (reductions to-basement zero-floor)
       (drop-while not-basement)
       first
       char-position))

