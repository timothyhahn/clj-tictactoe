(ns clj-tictactoe.ai
  (:gen-class)
  (use [clj-tictactoe.core :only (apply-move other-player is-winner?)]))

(defn indices 
  "From http://stackoverflow.com/questions/8641305/find-index-of-an-element-matching-a-predicate-in-clojure
   Gets indices of a collection that match a predicate"
  [pred coll]
     (keep-indexed #(when (pred %2) %1) coll))

(defn space-free? [n]
  (= " " n))

(defn utility 
  "Calculates the utility of the board for a specified player after a specific move"
  [board player move]
  (let [new-board (apply-move board move player)]
      (if (is-winner? new-board player)
        1
        (if (is-winner? new-board (other-player player))
          -1
          0))))

(defn generate-rules [board]
  (indices space-free? board))

(defn calculate-utilities 
  "Calculates the utilities of all possible moves"
  [board player rules]
  (let [n (count rules)]
    (apply map utility [(take n (repeat board)) (take n (repeat player)) rules])))

(defn winning-moves [utilities]
  (indices (fn [n] (= n 1)) utilities))

(defn generate-move 
  "Calculates a valid move for the AI" 
  [board player]
  (let [rules (generate-rules board) utilities (calculate-utilities board player rules)]
    (if (empty? (winning-moves utilities))
      (rand-nth (generate-rules board))
      (nth rules (rand-nth (winning-moves utilities))))))


