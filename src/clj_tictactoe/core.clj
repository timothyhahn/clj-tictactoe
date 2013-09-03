(ns clj-tictactoe.core
  (:gen-class))

(defn make-board []
  (vec (repeat 9 " ")))

(defn limit-str []
  (apply str(vec (repeat 5 "-"))))

;; Prints out the row with proper spacing/delimiting
(defn row-str [row]
  (let [rows (atom [])]
    (loop [n 0]
      (when (< n 3)
        (swap! rows conj (str(nth row n)))
        (if (< n 2)
          (swap! rows conj "|"))
        (recur (inc n))))
    (apply str @rows)))

(defn print-board [board]
  (do
    (loop [n 0]
      (when (< n 3)
        (println (row-str (second (split-at (* n 3) board))))
        (if (< n 2)
          (println (limit-str)))
        (recur (inc n))))))
  

(defn winner-of [board]
  nil)

(defn -main
  "Main class"
  [& args]
  (println "Welcome to Clojure Tic Tac Toe")
  (let [board (atom(make-board))]
    (while (= (winner-of board) nil)
      (println "Input Move(1-9):")
      (let [move (read-line)]
        (println move)))))
