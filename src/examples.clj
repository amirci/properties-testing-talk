(ns examples
  (:require
    [clojure.test.check :as tc]
    [clojure.test.check.generators :as g]
    [clojure.test.check.properties :as prop]))

(def sort-idempotent-prop
  (prop/for-all [v (g/vector g/int)]
    (= (sort v) (sort (sort v)))))

(def prop-sorted-first-less-than-last
  (prop/for-all [v (g/not-empty (g/vector g/int))]
    (let [s (sort v)]
      (< (first s) (last s)))))
