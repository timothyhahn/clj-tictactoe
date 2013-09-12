(ns clj-tictactoe.core
  (:gen-class))

(defn make-board 
  "Returns an empty board"
  []
  (vec (repeat 9 " ")))

(defn is-winner? 
  "Calculates if a specified player has won on a specified board"
  [board player]
  (let
    [top (subvec board 0 3)
     mid (subvec board 3 6)
     bot (subvec board 6 9)]
    (or
      ;;; Horizontal win
      ;; Check if there are any rows
      ;; where all of the elements
      ;; match the player element
      (some true? (map #(every? #{player} %) 
                    (partition 3 board)))

      ;;; Vertical Win
      ;; Check if there is a column
      ;; where all of the elements 
      ;; match the player element
      (some true? (map #(every? #{player} %) 
                    (letfn [(column [b] (take-nth 3 b))] 
                      (list (column board) 
                            (column (rest board)) 
                            (column (rest (rest board)))))))

      ;; Descending Diagonal win
      (=
         player
         (first top)
         (second mid)
         (last bot))
      ;; Ascendng Diagonal win
      (=
          player
          (last top)
          (second mid)
          (first bot)))))

(defn winner-of [board]
  "Returns either the character of the winner or nil if no winner"
  (if (is-winner? board "x")
    "x"
    (if (is-winner? board "o")
    "o"
    nil)))

(defn free? 
  "Returns whether or not the member on the board is equal to an empty character"
  [board n]
  (= (nth board n) " "))

(defn any-free? [board]
  (some #(= " " %) board))



(defn other-player [player]
  (if (= player "x")
    "o"
    "x"))

(defn apply-move 
  "Returns a board with the move performed if possible"
  [board move player]
  (if (free? board move)
      (assoc board move player)
      board))
