(ns clj-tictactoe.core
  (:gen-class :main true))

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
  (let [top (subvec board 0 3)
        mid (subvec board 3 6)
        bot (subvec board 6 9)
        ;rows [top mid bot]
        won (atom false)]
    ;; Horizontal win
    ;; TODO: Can't get for loop working inside this function
      (if (=
           player
           (first top)
           (second top)
           (last top))
        (swap! won (fn [_] true)))
      (if (=
           player
           (first mid)
           (second mid)
           (last mid))
        (swap! won (fn [_] true)))
      (if (=
           player
           (first bot)
           (second bot)
           (last bot))
        (swap! won (fn [_] true)))

    ;; Vertical win
    (if (=
           player
           (first top)
           (first mid)
           (first bot))
        (swap! won (fn [_] true)))
      (if (=
           player
           (second top)
           (second mid)
           (second bot))
        (swap! won (fn [_] true)))
      (if (=
           player
           (last top)
           (last mid)
           (last bot))
        (swap! won (fn [_] true)))

  ;; Descending Diagonal win
  (if (=
     player
     (first top)
     (second mid)
     (last bot))
    (swap! won (fn [_] true)))

  ;; Ascendng Diagonal win
  (if (=
      player
      (last top)
      (second mid)
      (first bot))
    (swap! won (fn [_] true)))

  @won))

(defn winner-of [board]
  "Returns either the character of the winner or nil if no winner"
  (if (is-winner? board "x")
    "x"
    (if (is-winner? board "o")
    "o"
    nil)))

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

(defn run-console []
  (println "Welcome to Clojure Tic Tac Toe")
    (let [board (atom (make-board)) player (atom "x")]
      (while (and
               (= (winner-of @board) nil)
               (any-free? @board)) 
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
        (let [winner (winner-of @board)]
          (if winner
            (println "Congrats to winner" winner)
            (println "No one wins")))))

(defn -main
  "Main class"
  [& args]
  (cond
       (= "console" (first args)) (run-console)
       true (run-console)))
