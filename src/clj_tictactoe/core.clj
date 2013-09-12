(ns clj-tictactoe.core
  (:gen-class :main true))

(defn make-board 
  "Returns an empty board"
  []
  (vec (repeat 9 " ")))

(defn limit-str 
  "Returns the string used to separate rows"
  []
  (apply str(vec (repeat 5 "-"))))

(defn row-str 
  "Returns the string of the row with the proper spacing/delimiting"
  [row]
  (let [rows (atom [])]
    (loop [n 0]
      (when (< n 3)
        (swap! rows conj (str(nth row n)))
        (if (< n 2)
          (swap! rows conj "|"))
        (recur (inc n))))
    (apply str @rows)))

(defn print-board 
  "Prints the board in its current state."
  [board]
  (do
    (loop [n 0]
      (when (< n 3)
        (println (row-str (second (split-at (* n 3) board))))
        (if (< n 2)
          (println (limit-str)))
        (recur (inc n))))))

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

(defn space-free? [n]
  (= " " n))

(defn indices 
  "From http://stackoverflow.com/questions/8641305/find-index-of-an-element-matching-a-predicate-in-clojure
   Gets indices of a collection that match a predicate"
  [pred coll]
     (keep-indexed #(when (pred %2) %1) coll))

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

(defn run-console []
  (println "Welcome to Clojure Tic Tac Toe")
    (println "Play with AI?(y/N)")
      (let [ai (if (= "y" (read-line)) true false)]
        (let [board (atom (make-board)) player (atom "x")]
          (while (and
                   (= (winner-of @board) nil)
                   (any-free? @board)) 
            (print-board @board)
              (if (or
                    (= @player "x")
                    (= ai false))
                (do
                  (println "Player" @player)
                  (println "Input Move(1-9):")
                  (try 
                    (let [move (dec(Integer/parseInt (read-line)))]
                      (if (and
                        (>= move 0)
                        (<= move 8)
                        (free? @board move))
                          (do 
                            (swap! board apply-move move @player)
                            (swap! player other-player))
                          (println "Please input a valid move")))
                    (catch NumberFormatException nfe (println "Please use a number to indicate your move"))))
                 (do 
                   (println "AI is making a move")
                   (swap! board apply-move (generate-move @board @player) @player)
                   (swap! player other-player))))
   
            (print-board @board)
            (let [winner (winner-of @board)]
              (if winner
                (println "Congrats to winner" winner)
                (println "No one wins"))))))

(defn -main
  "Main class"
  [& args]
  (cond
       (= "console" (first args)) (run-console)
       true (run-console)))
