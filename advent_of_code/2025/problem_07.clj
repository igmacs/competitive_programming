(defn problem7-pt1 [lines]
  (first
   (reduce
    (fn [[n-splits beams] line]
      (let [new-beams
            (flatten
             (map #(if (= \. (get line %)) % [(- % 1) (+ % 1)])
                  beams))]
        [(+ n-splits (- (count new-beams) (count beams)))
         (distinct new-beams)]))
    [0 [(.indexOf (first lines) "S")]]
    (rest lines))))

(defn problem7-pt2 [lines]
  (let [timelines
        (reduce
         (fn [beams line]
           (->> beams
                (mapcat (fn [[idx n]]
                          (if (= \. (get line idx))
                            [[idx n]]
                            [[(- idx 1) n] [(+ idx 1) n]])))
                sort
                (partition-by first)
                (map #(vector (first (first %)) (apply + (map second %))))))
         [[(.indexOf (first lines) "S") 1]]
         (rest lines))]
    (apply + (map second timelines))))


(with-open [r (clojure.java.io/reader "/tmp/input")]
  (let [init-ts (inst-ms (java.util.Date.))
        f (case (nth *command-line-args* 1)
            "1" problem7-pt1
            "2" problem7-pt2)
        ret (f (line-seq r))]
    (println "Run time:" (- (inst-ms (java.util.Date.)) init-ts) "milliseconds")
    (println ret)))
