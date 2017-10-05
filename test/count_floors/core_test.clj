(ns count-floors.core-test
  (:require 
    [clojure.test.check.generators :as g]
    [clojure.test :refer [deftest is]]
    [com.gfredericks.test.chuck.clojure-test :refer [checking]]
    [count-floors.core :refer :all]))

(def open-char \()

(def close-char \))

(defn floor-gen
  [open close]
  (g/fmap #(apply str %)
          (g/shuffle
            (concat
              (repeat open open-char)
              (repeat close close-char)))))

(deftest count-floors
  (checking "more open than close returns positive difference" 100
            [close g/s-pos-int 
             diff g/s-pos-int
             coll (floor-gen (+ diff close) close)]
            (is (= diff (count-total coll))))

  (checking "more close than open returns negative difference" 100
            [open g/s-pos-int 
             diff g/s-pos-int
             coll (floor-gen open (+ diff open))]
            (is (= (- diff) (count-total coll)))))

