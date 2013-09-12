(ns clj-tictactoe.ai-test
  (:require [clojure.test :refer :all]
            [clj-tictactoe.ai :refer :all])
  (:use [clj-tictactoe.core :only (make-board)]))

(def empty-board
  (make-board))

(def test-board
  ["x" "x" "o" " " "o" " " "o" "o" "x"])

(def full-board
  ["x" "o" "x" "o" "x" "o" "x" "o" "x"])



(deftest generate-rules-test
  (testing "The rules generated are valid"
    (is (= (generate-rules empty-board) [0 1 2 3 4 5 6 7 8]))
    (is (= (generate-rules test-board) [3 5]))
    (is (= (generate-rules full-board) []))))

(deftest calculate-utilities-test
  (testing "The utilities returned are correct"
    (is (= (calculate-utilities empty-board "x" (generate-rules empty-board)) [0 0 0 0 0 0 0 0 0]))
    (is (= (calculate-utilities empty-board "o" (generate-rules empty-board)) [0 0 0 0 0 0 0 0 0]))
    (is (= (calculate-utilities test-board "x" (generate-rules test-board)) [-1 -1]))
    (is (= (calculate-utilities test-board "o" (generate-rules test-board)) [1 1]))
    (is (= (calculate-utilities full-board "x" (generate-rules full-board)) []))
    (is (= (calculate-utilities full-board "o" (generate-rules full-board)) []))))

(deftest generate-move-test
  (testing "The move returned is a correct move"
    (let [move (generate-move empty-board "x")]
      (is (some #{move} (generate-rules empty-board))))
    (let [move (generate-move test-board "x")]
      (is (some #{move} (generate-rules test-board))))))

