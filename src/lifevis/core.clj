(ns lifevis.core
  (:require [seesaw.timer :as t]
            [seesaw.core :refer :all])
  (:gen-class))


(defn build-widgets [data]
  (->> data
       (apply concat)
       (map #(if % :black :white))
       (map #(label :background %))))

(defn random-seq []
  (repeatedly #(rand-nth [true false])))

(def starting-data
  (map vector (take 64 (random-seq))))

(defn dummy-simulate [data]
  (println data)
  (map vector (take 64 (random-seq))))

(defn display-game
  "Displays a SWING window with game of life cells"
  [rows]
  (invoke-later
    (let [grid (grid-panel :rows rows)
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

(defn -main
  [& args]
  (display-game 8))
