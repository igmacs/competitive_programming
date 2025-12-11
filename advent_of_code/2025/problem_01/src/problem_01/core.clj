(ns problem-01.core
  (:gen-class))

(defn problem1 [lines current acc]
  (let [next-line (first lines)]
    (if (not next-line)
      acc
      (let [distance (Integer/parseInt (subs next-line 1))
            sum (case (subs next-line 0 1)
                  "L" (- distance)
                  "R" distance)
            new (+ current sum)]
        (recur
         (rest lines)
         new
         (if (= 0 (rem new 100)) (+ acc 1) acc))))))


(defn problem1-part2 [lines current acc]
  (let [next-line (first lines)]
    (if (not next-line)
      acc
      (let [distance (Integer/parseInt (subs next-line 1))
            move (case (subs next-line 0 1)
                   "L" (- distance)
                   "R" distance 100)
            new (+ current move)]
        (recur
         (rest lines)
         (rem new 100)
         (+
          acc
          (int (/ (- (abs new) 1) 100))
          (if (= (rem new 100) 0) 1 0)
          (if (< (* current new) 0) 1 0)))))))


(defn -main
  [problem]
  (with-open [r (clojure.java.io/reader "resources/input")]
    (case problem
      "1" (println (problem1 (line-seq r) 50 0))
      "2" (println (problem1-part2 (line-seq r) 50 0)))))
