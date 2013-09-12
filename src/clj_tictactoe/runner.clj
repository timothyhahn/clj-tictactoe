(ns clj-tictactoe.runner
  (use [clj-tictactoe.console :only (run-console)])
  (:gen-class :main true))

(defn -main
  "Main class"
  [& args]
  (cond
       (= "console" (first args)) (run-console)
       true (run-console)))
