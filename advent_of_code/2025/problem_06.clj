(defn transpose [matrix]
  (if (not matrix)
    (repeat nil)
    (map cons (first matrix) (transpose (next matrix)))))


(defn problem6-pt1 [lines]
  (->> lines
       (map #(drop-while clojure.string/blank? (clojure.string/split % #" +")))
       reverse
       transpose
       (map
        (fn [line]
          (apply (eval (read-string (first line))) (map #(Long/parseLong %) (rest line)))
          ))
       (apply +)))

(defn problem6-pt2 [lines]
  (let [numbers (->> (range (apply max (map count lines)))
                     (map
                      (fn [idx]
                        (some->> (butlast lines)
                             (map #(get % idx)) ;; returns nil if out of bounds
                             (drop-while #(or (nil? %) (= \space %)))
                             reverse
                             (drop-while #(or (nil? %) (= \space %)))
                             reverse
                             (map #(if (= \space %) \0 %))
                             seq
                             (apply str)
                             Long/parseLong)))
                     (partition-by nil?)
                     (take-nth 2))
        ops (->> (clojure.string/split (last lines) #" +")
              (map read-string)
              (map eval))]
    (apply + (map apply ops numbers))))


(with-open [r (clojure.java.io/reader "/tmp/input")]
  (let [init-ts (inst-ms (java.util.Date.))
        f (case (nth *command-line-args* 1)
            "1" problem6-pt1
            "2" problem6-pt2)
        ret (f (line-seq r))]
    (println "Run time:" (- (inst-ms (java.util.Date.)) init-ts) "milliseconds")
    (println ret)))
