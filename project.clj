(defproject cljs-io "1.2.3"
  :plugins [[lein-cljsbuild "1.0.0"]]
  :dependencies [[org.clojure/clojurescript "0.0-2067"]]
  :cljsbuild {
    :builds [{
        ; The path to the top-level ClojureScript source directory:
        :source-paths ["src"]
        ; The standard ClojureScript compiler options:
        ; (See the ClojureScript compiler documentation for details.)
        :compiler {
          :output-to "war/javascripts/main.js"  ; default: target/cljsbuild-main.js
          ;:optimizations :advanced
          :pretty-print true}}]})