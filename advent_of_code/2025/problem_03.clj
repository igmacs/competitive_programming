(defn problem3-pt1-single-line [line]
  (first
   (reduce
    (fn [[max d u] digit]
      [(if (<= digit u) max (+ digit (* 10 d)))
       (clojure.core/max d digit)
       (if (> digit d) 0 (clojure.core/max u digit))])
    [0 0 0]
    line)))

(defn problem3-pt2-single-line [line]
  (first
   (reduce
    (fn [[total idx] it]
      (let [remaining-line (drop idx line)
            allowed-line (take (+ (count remaining-line) -12 it 1) remaining-line)
            [maxidx max] (apply max-key #(nth % 1) (reverse (map-indexed vector allowed-line)))]
        [(+ (* 10 total) max)
         (+ 1 idx maxidx)]))
    [0 0]
    (range 12))))


(with-open [r (clojure.java.io/reader "/tmp/input")]
  (let [f (case (nth *command-line-args* 1)
            "1" problem3-pt1-single-line
            "2" problem3-pt2-single-line)]
    (println
     (apply +
            (map
             (comp f (partial map #(Integer/parseInt (str %))))
             (line-seq r))))))
