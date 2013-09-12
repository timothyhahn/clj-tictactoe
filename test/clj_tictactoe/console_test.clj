(ns clj-tictactoe.console-test
  (:require [clojure.test :refer :all]
            [clj-tictactoe.console :refer :all])
  (:use [clj-tictactoe.core :only (make-board)]))

(def empty-board
  (make-board))

(def empty-board-str 
" | | 
-----
 | | 
-----
 | | 
")

(def test-board
  ["x" "x" "o" " " "o" " " "o" "o" "x"])

(def test-board-str
"x|x|o
-----
 |o| 
-----
o|o|x
")

(deftest print-board-test
  (testing "The board prints properly"
    (is (= (with-out-str(print-board empty-board)) (str empty-board-str)))
    (is (= (with-out-str(print-board test-board)) (str test-board-str)))))

