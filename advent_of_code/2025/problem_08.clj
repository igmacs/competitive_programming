(defn cartesian [[first & rest]]
  (if (not rest)
    nil
    (lazy-seq
     (concat
      (map (partial list first) rest)
      (cartesian rest)
      ))))


(defn distance [[x1 y1 z1] [x2 y2 z2]]
  (letfn [(diff-sq [x y] (* (- x y) (- x y)))]
    (Math/sqrt (+ (diff-sq x1 x2) (diff-sq y1 y2) (diff-sq z1 z2)))))

(defn problem8-pt1 [points]
  (->> points
       cartesian
       (map (fn [[x y]] [(distance x y) x y]))
       sort
       (take 1000)
       (map rest)
       (reduce
        (fn [circuits [point1 point2]]
          (let [{appearing true missing false}
                (group-by #(or (contains? % point1) (contains? % point2)) circuits)]
            (cons (into #{point1 point2} (apply into appearing)) missing)))
        [])
       (map count)
       sort
       reverse
       (take 3)
       (apply *)))

(defn problem8-pt2 [points]
  (->> points
       (map (fn [point]
              ;; Get closest point and distance to it for each point
              (->> points
                   (remove #(= % point))
                   (map #(vector (distance point %) point %))
                   (apply min-key first))))
       ;; Get point for which that distance is higher
       (apply max-key first)
       rest
       (map first)
       (apply *)))

(with-open [r (clojure.java.io/reader "/tmp/input")]
  (let [init-ts (inst-ms (java.util.Date.))
        f (case (nth *command-line-args* 1)
            "1" problem8-pt1
            "2" problem8-pt2)
        points (map
                (fn [line]
                  (map #(Long/parseLong %) (clojure.string/split line #",")))
                (line-seq r))
        ret (f points)]
    (println "Run time:" (- (inst-ms (java.util.Date.)) init-ts) "milliseconds")
    (println ret)))
