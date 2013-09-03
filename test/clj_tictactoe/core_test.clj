(ns clj-tictactoe.core-test
  (:require [clojure.test :refer :all]
            [clj-tictactoe.core :refer :all]))

(def empty-board-str 
" | | 
-----
 | | 
-----
 | | 
")

(def test-board
  ["x" "x" "o" " " "x" " " "o" "o" "x"])

(def test-board-str
"x|x|o
-----
 |x| 
-----
o|o|x
")


(deftest boardprint
  (testing "The board prints properly"
    (is (= (with-out-str(print-board (make-board))) (str empty-board-str)))
    (is (= (with-out-str(print-board test-board)) (str test-board-str)))))

(deftest winner
  (testing "The winner is properly returned"
    (is (= (winner-of (make-board)) nil))))
    

