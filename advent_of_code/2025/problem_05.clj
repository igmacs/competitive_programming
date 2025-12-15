(defn build-intervals [ranges]
  (->> ranges
    (map #(clojure.string/split % #"-"))
    (map (partial map #(Long/parseLong %)))
    (sort-by first)
    (reduce
     (fn [disjunct-intervals [beg end]]
       (let [[last_beg last_end] (first disjunct-intervals)]
         (if (< last_end beg)
           (cons [beg end] disjunct-intervals)
           (cons [last_beg (max last_end end)] (rest disjunct-intervals)))))
     '([-1 -1]))
    reverse
    rest
    (apply vector)))

(defn problem5-pt1 [ranges ingredients]
  (let [intervals (build-intervals ranges)
        interval-begs (apply vector (map first intervals))
        out-of-bounds (count intervals)]
    (count
     (filter
      (fn [ingredient]
        (let [bs-ret (java.util.Collections/binarySearch
                   interval-begs ingredient compare)
              idx (if (>= bs-ret 0) bs-ret (- -2 bs-ret))]
          (and (< idx out-of-bounds)
               (>= idx 0)
               (<= ingredient (nth (nth intervals idx) 1)))))
      (map #(Long/parseLong %) ingredients)))))

(defn problem5-pt2 [ranges _]
  (let [intervals (build-intervals ranges)]
    (apply + (map (fn [[x y]] (+ y (- x) 1)) intervals))))


(with-open [r (clojure.java.io/reader "/tmp/input")]
  (let [init-ts (inst-ms (java.util.Date.))
        f (case (nth *command-line-args* 1)
            "1" problem5-pt1
            "2" problem5-pt2)
        [ranges others] (split-with
                            #(not= "" %)
                            (line-seq r))
        ingredients (rest others)
        ret (f ranges ingredients)]
    (println "Run time:" (- (inst-ms (java.util.Date.)) init-ts) "milliseconds")
    (println ret)))
