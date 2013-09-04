(ns clj-tictactoe.core-test
  (:require [clojure.test :refer :all]
            [clj-tictactoe.core :refer :all]))

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

(def full-board
  ["x" "o" "x" "o" "x" "o" "x" "o" "x"])

(deftest print-board-test
  (testing "The board prints properly"
    (is (= (with-out-str(print-board empty-board)) (str empty-board-str)))
    (is (= (with-out-str(print-board test-board)) (str test-board-str)))))

(deftest winner-test
  (testing "Whether or not we can tell whether a player is a winner"
    (is (= (is-winner? empty-board "x") false))
    (is (= (is-winner? empty-board "o") false)))

  (testing "The winner is properly returned"
    (is (= (winner-of empty-board) nil))
    (is (= (winner-of test-board) "o"))
    (is (= (winner-of full-board) "x"))
    (is (= (winner-of ["x" "x" "x" " " " " " " " " " " " "]) "x"))
    (is (= (winner-of [" " " " " " "o" "o" "o" " " " " " "]) "o"))
    (is (= (winner-of [" " " " " " "x" "o" "x" "o" "o" "o"]) "o"))
    (is (= (winner-of ["x" " " " " "x" " " " " "x" " " " "]) "x"))
    (is (= (winner-of [" " "o" " " " " "o" " " " " "o" " "]) "o"))
    (is (= (winner-of [" " "x" "o" " " " " "o" " " "x" "o"]) "o"))))

(deftest apply-move-test
  (testing "Whether applying a move works"
    (is (= (apply-move empty-board 0 "x") ["x" " " " " " " " " " " " " " " " "]))
    (is (= (apply-move test-board 0 "x") test-board))
    (is (= (apply-move test-board 3 "x") ["x" "x" "o" "x" "o" " " "o" "o" "x"]))
    (is (= (apply-move full-board 0 "x") full-board))))

(deftest freeness-test
  (testing "If specific cells are free"
    (is (= (free? empty-board 0) true))
    (is (= (free? test-board 0) false))
    (is (= (free? test-board 3) true))
    (is (= (free? full-board 0) false)))

  (testing "If any cells are free"
    (is (= (any-free? empty-board) true))
    (is (= (any-free? test-board) true))
    (is (= (any-free? full-board) nil))))
    
(deftest other-player-test
  (testing "The other player is returned correctly"
    (is (= (other-player "x") "o"))
    (is (= (other-player "o") "x"))))

