(ns count-floors.core)

(defn count-total
  [s]
  (reduce #(+ %1 (if (= %2 \() 1 -1)) 0 s))
