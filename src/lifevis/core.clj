(ns lifevis.core
  (:use seesaw.core)
  (:require [seesaw.bind :as b]
            [seesaw.timer :as t])
  (:gen-class))

(def starting-data [[true false] [false true]])

(defn build-widgets [data]
  (->> data
       (apply concat)
       (map #(if % :black :white))
       (map #(label :background %))))

(defn random-seq []
  (repeatedly #(rand-nth [true false])))

(defn dummy-simulate [data]
  (println data)
  (map vector (take 4 (random-seq))))

(defn -main
  "Displays a SWING window with game of life cells"
  [& args]
  (invoke-later
    (let [grid (grid-panel :rows 2)
          data (ref starting-data)]
      (-> (frame :title "Game of Life"
                 :content grid
                 :on-close :exit
                 :minimum-size [300 :by 300])
          pack!
          show!)
      (config! grid :items (build-widgets @data))
      (t/timer (fn [state]
                 (dosync
                   (alter data dummy-simulate))
                 (config! grid :items (build-widgets @data)))))))
