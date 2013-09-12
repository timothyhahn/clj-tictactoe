(ns clj-tictactoe.console
  (require [clj-tictactoe.core :as core]
           [clj-tictactoe.ai :as ai]))

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

(defn run-console []
  (println "Welcome to Clojure Tic Tac Toe")
    (println "Play with AI?(y/N)")
      (let [ai (if (= "y" (read-line)) true false)]
        (let [board (atom (core/make-board)) player (atom "x")]
          (while (and
                   (= (core/winner-of @board) nil)
                   (core/any-free? @board)) 
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
                        (core/free? @board move))
                          (do 
                            (swap! board core/apply-move move @player)
                            (swap! player core/other-player))
                          (println "Please input a valid move")))
                    (catch NumberFormatException nfe (println "Please use a number to indicate your move"))))
                 (do 
                   (println "AI is making a move")
                   (swap! board core/apply-move (ai/generate-move @board @player) @player)
                   (swap! player core/other-player))))
   
            (print-board @board)
            (let [winner (core/winner-of @board)]
              (if winner
                (println "Congrats to winner" winner)
                (println "No one wins"))))))


