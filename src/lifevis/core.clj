(ns lifevis.core
  (:use seesaw.core)
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (invoke-later
    (-> (frame :title "Foo"
               :content (grid-panel :rows 2 :items [(label :background :white) "Two" "Three" (label :background :black)])
               :on-close :exit
               :minimum-size [300 :by 300])
        pack!
        show!))
  (println "Hello, World!"))
