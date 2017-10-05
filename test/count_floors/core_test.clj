(ns count-floors.core-test
  (:require 
    [clojure.test.check.generators :as g]
    [clojure.test :refer [deftest is testing]]
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

(defn basement-gen
  [n]
  (g/fmap #(clojure.string/join [% close-char])
          (g/return
            (apply str
                   (concat
                     (repeat n open-char)
                     (repeat n close-char))))))

(def input-file
  (-> "test/day1.input.txt"
      slurp
      clojure.string/split-lines
      first))

(deftest count-total-floors
  (testing "Reading the input file" 
    (is (= 138 (count-total input-file))))

  (checking "more open than close returns positive difference" 20
            [close g/s-pos-int 
             diff g/s-pos-int
             coll (floor-gen (+ diff close) close)]
            (is (= diff (count-total coll))))

  (checking "more close than open returns negative difference" 20
            [open g/s-pos-int 
             diff g/s-pos-int
             coll (floor-gen open (+ diff open))]
            (is (= (- diff) (count-total coll))))

  (checking "same close as open returns zero" 20
            [open g/s-pos-int coll (floor-gen open open)]
            (is (zero? (count-total coll)))))

(deftest find-basement-index
  (testing "Walking to the basement first thing"
    (is (= 1 (find-basement ")"))))

  (testing "Walking to the basement after a few floors"
    (is (= 5 (find-basement "()())"))))

  (testing "Reading the input file" 
    (is (= 1771 (find-basement input-file))))

  (checking "The basement is the first position after same amount open and close" 40
            [n g/s-pos-int coll (basement-gen n)]
            (is (= (inc (* 2 n)) (find-basement coll)))))
