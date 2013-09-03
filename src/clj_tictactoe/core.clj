(ns clj-tictactoe.core
  (:gen-class))

(defn make-board []
  "Returns an empty board"
  (vec (repeat 9 " ")))

(defn limit-str []
  "Returns the string used to separate rows"
  (apply str(vec (repeat 5 "-"))))

(defn row-str [row]
  "Returns the string of the row with the proper spacing/delimiting"
  (let [rows (atom [])]
    (loop [n 0]
      (when (< n 3)
        (swap! rows conj (str(nth row n)))
        (if (< n 2)
          (swap! rows conj "|"))
        (recur (inc n))))
    (apply str @rows)))

(defn print-board [board]
  "Prints the board in it's current state.
  SIDE EFFECTS: Prints to console"
  (do
    (loop [n 0]
      (when (< n 3)
        (println (row-str (second (split-at (* n 3) board))))
        (if (< n 2)
          (println (limit-str)))
        (recur (inc n))))))
  
(defn is-winner? [board player]
  false)

(defn winner-of [board]
  "Returns either the character of the winner or nil if no winner"
  nil)

(defn free? [board n]
  "Returns whether or not the member on the board is equal to an empty character"
  (= (nth board n) " "))

(defn any-free? [board]
  "True if there is a free spot on the board, otherwise nil"
  (some #(= " " %) board))

(defn apply-move [board n player]
  "Returns a board with the move performed if possible"
  (if (free? board n)
      (assoc board n player)
      board))

(defn other-player [player]
  (if (= player "x")
    "o"
    "x"))

(defn -main
  "Main class"
  [& args]
  (println "Welcome to Clojure Tic Tac Toe")
  (let [board (atom (make-board)) player (atom "x")]
    (while (and
             (any-free? @board)
             (= (winner-of @board) nil))
      (print-board @board)
      (println "Player" @player)
      (println "Input Move(1-9):")
      (try 
        (let [move (dec(Integer/parseInt (read-line)))]
          (if (and
            (>= move 0)
            (<= move 8)
            (free? @board move))
              (do (swap! board apply-move move @player)
               (swap! player other-player))
              (println "Please input a valid move")))
        (catch NumberFormatException nfe (println "Please use a number to indicate your move"))))
 
      (print-board @board)
      (let [winner (winner-of board)]
        (if winner
          (println "Congrats to winner" winner)
          (println "No one wins")))))
