(defproject clj-tictactoe "1.0.0-SNAPSHOT"
  :description "A simple tictactoe program"
  :url "http://github.com/timothyhahn/clj-tictactoe"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                [org.clojure/tools.nrepl "0.2.3"]]

  :main clj-tictactoe.core
  :profiles {:uberjar {:aot :all}})
