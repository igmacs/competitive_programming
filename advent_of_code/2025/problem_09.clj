(defn cartesian [[first & rest]]
  (if (not rest)
    nil
    (lazy-seq
     (concat
      (map (partial list first) rest)
      (cartesian rest)
      ))))


(defn problem9-pt1 [points]
  (->> points
       cartesian
       (map (fn [[[x1 y1] [x2 y2]]] (abs (* (+ 1 x1 (- x2)) (+ 1 y1 (- y2))))))
       (apply max)))

;; Part 2

;; Assumptions (if my answer is accepted, I'll consider them
;; validated):
;; 1: The points alternate changing row and column
;; 2: A "border" always blocks a rectangle, even if there is other
;;   adjacent border. For example if I have 1,1 - 1,4 - 3,4 - 3,2 -
;;   4,2 - 4,4 - 6,4 - 6-1, the greatest rectangle isn't 1,1 - 6,4,
;;   but 1,1 - 3,4 or 6,1 - 4-4
;; 3: The perimeter that is built never intersects itself

;; Algorithm: Check every pair of points as candidates for the
;; rectangle, and discard the ones that are not valid, then apply part
;; 1. A pair of candidates isn't valid if
;; 1: A loop segment intersects with the rectangle border. Under
;;   assumption 2, it would necessarily mean that some tiles around
;;   that segment are not red or green
;; 2: A segment from the loop overlaps with the rectangle perimeter,
;;   and with the "wrong" orientation. That would mean the side of
;;   that segment which is inside the rectangle is outside the loop,
;;   so the rectangle is not fully inside the loop. By orientation we
;;   mean the following: if we consider the direction of each segment
;;   as we build the loop (right, left, up, down), it's easy to
;;   convince yourself with a drawing that (under assumption 3)
;;   segments that have the same direction always have the interior of
;;   the loop on the same side (e.g., all segments going left always
;;   have the loop interior below them). And we can get the "right"
;;   orientation from the segments whicb are furthest in one
;;   direction (e.g., the leftmost vertical segment will necessarily
;;   have the interior of the loop on its right)

(defrecord Segment [dir dim fixed interval])

(defn segment-from-points [[x1 y1] [x2 y2]]
  (cond
    (and (= x1 x2) (< y1 y2))
    (Segment. :up :vertical x1 [y1 y2])
    (and (= x1 x2) (> y1 y2))
    (Segment. :down :vertical x1 [y2 y1])
    (and (= y1 y2) (< x1 x2))
    (Segment. :right :horizontal y1 [x1 x2])
    (and (= y1 y2) (> x1 x2))
    (Segment. :left :horizontal y1 [x2 x1])))

(defrecord Rectangle [x_beg x_end y_beg y_end])

(defn rectangle-from-points [[x1 y1] [x2 y2]]
  (Rectangle. (min x1 x2) (max x1 x2) (min y1 y2) (max y1 y2)))

(defn width [{:keys [x_beg x_end y_beg y_end]}]
  (min (- x_end x_beg)) (- y_end y_beg))

(defn intersect? [{dim :dim fixed :fixed [a b] :interval}
                  {:keys [x_beg x_end y_beg y_end]}]
  (cond
    (= :vertical dim)
    (and (< x_beg fixed x_end)
         (not (<= y_beg y_end a b))
         (not (<= a b y_beg y_end)))
    (= :horizontal dim)
    (and (< y_beg fixed y_end)
         (not (<= x_beg x_end a b))
         (not (<= a b x_beg x_end)))
    ))


(defn overlap? [{dim :dim fixed :fixed [a b] :interval}
                  {:keys [x_beg x_end y_beg y_end]}]
  (cond
    (and (= :vertical dim) (= x_beg fixed) (<= y_beg a b y_end))
    :left
    (and (= :vertical dim) (= x_end fixed) (<= y_beg a b y_end))
    :right
    (and (= :horizonal dim) (= y_beg fixed) (<= x_beg a b x_end))
    :up
    (and (= :horizontal dim) (= y_end fixed) (<= x_beg a b x_end))
    :down))


(defn build-segment-indexer [segments]
  (let [{vs :vertical hs :horizontal} (group-by :dim segments)]
    {:vertical (apply vector (sort-by :fixed vs))
     :horizontal (apply vector (sort-by :fixed hs))}))

(defn segments-touching-rectangle [{:keys [x_beg x_end y_beg y_end]}
                                   {sorted-vs :vertical sorted-hs :horizontal}]
  (let [bs (fn [zs z]
             (- -1 (java.util.Collections/binarySearch
                    zs {:fixed z} #(compare (:fixed %1) (:fixed %2)))))
        x-min (bs sorted-vs (- x_beg 0.1))
        x-max (bs sorted-vs (+ x_end 0.1))
        y-min (bs sorted-hs (- y_beg 0.1))
        y-max (bs sorted-hs (+ y_end 0.1))]
    (concat
     (->> (subvec sorted-vs x-min x-max)
                     (remove (fn [{[y3 y4] :interval}]
                             (or (<= y_beg y_end y3 y4)
                                 (<= y3 y4 y_beg y_end)))))
     (->> (subvec sorted-hs y-min y-max)
                   (remove (fn [{[x3 x4] :interval}]
                             (or (<= x_beg x_end x3 x4)
                                 (<= x3 x4 x_beg x_end))))))))

(defn rectangle-area [[x1 y1] [x2 y2]]
  (* (inc (abs (- x1 x2))) (inc (abs (- y1 y2)))))

(defn problem9-pt2 [points]
  (let [segments (map segment-from-points
                      (cons (last points) (butlast points))
                      points)
        segment-indexer (build-segment-indexer segments)
        leftmost-segment (->> segments
                              (filter #(= :vertical (:dim %)))
                              (apply min-key :fixed))
        topmost-segment (->> segments
                              (filter #(= :horizontal (:dim %)))
                              (apply min-key :fixed))]
    (->> points
         cartesian
         (remove (fn [[[x1 y1] [x2 y2]]]
                   (let [rectangle (rectangle-from-points [x1 y1] [x2 y2])
                         segments (segments-touching-rectangle rectangle segment-indexer)]
                     (or
                      ;; Case 1 of the algorithm described above
                      (->> segments
                           (filter #(intersect? % rectangle))
                           first)
                      ;; Case 2 of the algorithm described above
                      (->> segments
                           (filter #(if-let [border (overlap? % rectangle)]
                                      (and (> (width rectangle) 1)
                                           (case border
                                             :left (not= (:dir %) (:dir leftmost-segment))
                                             :right (= (:dir %) (:dir leftmost-segment))
                                             :up (not= (:dir %) (:dir topmost-segment))
                                             :down (= (:dir %) (:dir topmost-segment))))))
                           first
                      )))))
         (map #(apply rectangle-area %))
         (apply max))))

(with-open [r (clojure.java.io/reader "/tmp/input")]
  (let [init-ts (inst-ms (java.util.Date.))
        f (case (nth *command-line-args* 1)
            "1" problem9-pt1
            "2" problem9-pt2)
        points (map
                (fn [line]
                  (map #(Long/parseLong %) (clojure.string/split line #",")))
                (line-seq r))
        ret (f points)]
    (println "Run time:" (- (inst-ms (java.util.Date.)) init-ts) "milliseconds")
    (println ret)))
