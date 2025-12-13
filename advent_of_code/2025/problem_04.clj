(use '[leiningen.exec :only (deps)])
(deps '[[org.clojure/math.combinatorics "0.3.0"]])
(require '[clojure.math.combinatorics :as combo])

(defn problem4-pt1 [grid-v-str]
  (let [grid
        ;; string access is slightly slower than vector access
        (apply vector (map #(apply vector (seq %)) grid-v-str))
        h (count grid)
        w (count (first grid))]
    (first
     (reduce
      (fn [[sum i j] _]
        [
         (if (and
              (= \@ (get (get grid i) j))
              (> 5 (count (filter
                           ;; get out of range returns nil, no error
                           (fn [[r c]] (= \@ (get (get grid (+ i r)) (+ j c))))
                           (combo/cartesian-product [-1 0 1] [-1 0 1])))))
           (+ 1 sum)
           sum)
         (if (= j (- w 1)) (+ i 1) i)
         (if (= j (- w 1)) 0 (+ j 1))])
      [0 0 0]
      (range (* h w)))))) ;; using cartesian product here is slower

;; Part 2 would be trivial to do in a suboptimal way building on part
;; 1, at least in a language with mutable vectors. In Python, that
;; approach takes less than half a second to run, but here it takes a
;; few seconds, so I tried a few implementations to try to improve it
;; but none worked. Later I realized that part 1 is also much slower
;; than Python, and part 2 builds on part 1, so I guess the problem
;; wasn't with how to make the vector mutable after all

(defn build-grid [how grid]
  (case how
    :pure
    (apply vector (map #(apply vector (seq %)) grid))
    :transient
    (transient (apply vector (map #(transient (apply vector (seq %))) grid)))
    :atom
    (apply vector (map #(apply vector (map atom %)) grid))))

(defn get-grid [how grid i j]
  (case how
    :pure (get (get grid i) j)
    :transient (get (get grid i) j)
    :atom (let [c (get (get grid i) j)]
            (when c (deref c)))))

(defn update-grid [how grid i j]
  (case how
    :pure
    (assoc grid i (assoc (get grid i) j \.))
    :transient
    (assoc! grid i (assoc! (get grid i) j \.))
    :atom
    (do (swap! (get (get grid i) j) (constantly \.))
        grid)))

(defn problem4-pt2-single-pass [how grid h w]
  (reduce
   (fn [[sum grid] [i j]]
     (if (and
          (= \@ (get-grid how grid i j))
          (> 5 (count (filter
                       (fn [[r c]] (= \@ (get-grid how grid (+ i r) (+ j c))))
                       (combo/cartesian-product [-1 0 1] [-1 0 1])))))
       [(+ 1 sum) (update-grid how grid i j)]
       [sum grid]))
   [0 grid]
   (combo/cartesian-product (range h) (range w))))


(defn problem4-pt2
  ([how grid] (problem4-pt2
               how
               (build-grid how grid)
               (count grid)
               (count (first grid))
               0))
  ([how grid h w prev-sum]
   (let [[new-sum new-grid] (problem4-pt2-single-pass how grid h w)]
     (if (= 0 new-sum)
       prev-sum
       (recur how new-grid h w (+ prev-sum new-sum))))))



(with-open [r (clojure.java.io/reader "/tmp/input")]
  (let [init-ts (inst-ms (java.util.Date.))
        f (case (nth *command-line-args* 1)
            "1" problem4-pt1
            "2" (partial problem4-pt2 :pure))
        grid (apply vector (line-seq r))
        ret (f grid )]
    (println (- (inst-ms (java.util.Date.)) init-ts))
    (println ret)))
