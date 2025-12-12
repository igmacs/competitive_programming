;; Part 1

(defn log10 [n]
  (if (< n 10)
    1
    (+ 1 (log10 (int (/ n 10))))))

(defn *exp10 [n k]
  (if (= k 0)
    n
    (*exp10 (* n 10) (- k 1))))



(defn problem2-single-range [beg end]
  (let [m (log10 beg)
        p (log10 end)]
    (cond
      ;; If the interval bounds are not of the same order of magnitude
      ;; we split the interval and process it
      ;; recursively (e.g., [25,150] is equivalent to [25,99]
      ;; and [101, 150] (10^N can never be a repeated sequence)
      (not= m p)
      (+
       (problem2-single-range beg (- (*exp10 1 m) 1))
       (problem2-single-range (+ (*exp10 1 m) 1) end))
      ;; If the number of digits of the numbers in the interval is
      ;; odd, it can't be a repeated sequence
      (= (rem m 2) 1) 0
      ;; Actual logic, following this reasoning: if we have
      ;; e.g., [1234, 5678], all numbers XX where X is in [12+1, 56-1]
      ;; will be repeated sequences in that range. 1212 and 5656 might
      ;; be also a match or might not, and needs to be checked for
      ;; each interval. The sum of all those XX numbers will be
      ;; sum(100*X+X) = 100*sum(X) + sum(X), where sum(X) =
      ;; sum(1..55)-sum(1..12), where the formula for sum(1..N) is
      ;; known: N*(N+1)/2
      :else
      (let [p (/ m 2)
            b (int (/ beg (*exp10 1 p)))
            e (int (/ end (*exp10 1 p)))
            sum-up-to-b (/ (+ (* b b) b) 2)
            sum-up-to-e (/ (+ (* e e) e) 2)]
        (+
         ;; Sum of all repeated sequences that are trivially in the
         ;; range
         sum-up-to-e
         (*exp10 sum-up-to-e p)
         (- sum-up-to-b)
         (- (*exp10 sum-up-to-b p))
         ;; The first candidate is always substracted above, so we
         ;; need to add it again if it's actually in the range
         (let [b-candidate (+ b (*exp10 b p))]
           (if (<= beg b-candidate) b-candidate 0))
         ;; The last candidate is always added in the first sum, so we
         ;; need to substract it if it isn't actually in the range
         (let [e-candidate (+ e (*exp10 e p))]
           (if (< end e-candidate) (- e-candidate) 0)))))))


(defn problem2-pt1 [ranges acc]
  (let [range (first ranges)]
    (if (not range)
      acc
      (let [
            interval (clojure.string/split range #"-")
            beg (Long/parseLong (first interval))
            end (Long/parseLong (first (rest interval)))]
        (recur
         (rest ranges)
         (+
          acc
          ;; We assume the ranges are disjunct, which is not actually
          ;; said anywhere in the exercise
          (problem2-single-range beg end)))))))


;; Part 2

;; A generalization of the previous part. Before, it was only sequence
;; repeated twice, now it's sequences repeated k times.

;; If the order of magnitude of the range is n, k must be a divisor of
;; n, so we need to factorize n to know the possible ranges
(defn factorize
  ([n] (factorize n 2 '()))
  ([n m factors]
   (cond
     (< n m)
     factors
     (= 0 (rem n m))
     (recur
      (/ n m)
      m
      (if (= m (first factors))
        factors
        (conj factors m)))
     :else
     (recur
      n
      (+ m 1)
      factors))))

;; Before, we had to compute sum(X*10² + X) = sum(X)*(10² + 1). Now
;; the repeated sequences will be X*(1+10^p+10^2p+...+10^kp), where
;; kp=n. We will need to be able to compute the second factor, but
;; fortunately there is a formula for it: 1+N+N²+...+N^r
;; = (1-N^r)/(1-N)
(defn sum-series-10 [p m]
  (/ (- 1 (long (Math/pow 10 (+ m p)))) (- 1 (long (Math/pow 10 p)))))


;; Same as part1, but with q as number of repetitions instead of 2. m
;; is just the order of magnitued, to avoid computing it again
(defn problem2-pt2-single-factor [beg end m q]
  (let [p (/ m q)
        b (int (/ beg (*exp10 1 (- m p))))
        e (int (/ end (*exp10 1 (- m p))))
        sum-up-to-b (/ (+ (* b b) b) 2)
        sum-up-to-e (/ (+ (* e e) e) 2)
        sum-series-10 (sum-series-10 p (- m p))]
    (+
     (* sum-up-to-e sum-series-10)
     (- (* sum-up-to-b sum-series-10))
     (let [b-candidate (* b sum-series-10)]
       (if (<= beg b-candidate) b-candidate 0))
     (let [e-candidate (* e sum-series-10)]
       (if (< end e-candidate) (- e-candidate) 0)))))


;; Sum of previous function for all factors in a list
(defn problem2-pt2-single-level [beg end m factors]
  (let [p (first factors)]
    (if (not p)
      0
      (+
       (problem2-pt2-single-factor beg end m p)
       (problem2-pt2-single-level beg end m (rest factors))))))

;; If you count the repeated sequences for each divisor of m and add
;; them all together, some of them will be counted more than
;; once (e.g., 111111 can be 111 twice or 11 three times). The formula
;; that I came up with to get all of them exactly once is the
;; following:
;; - first, get all the prime divisors of m, and ignore their exponent
;; - second, get all the repeated sequences for those primes only
;; - then, substract all the repeated sequences for divisors that are
;;   the product of two of those prime numbers, as they will have been
;;   counted twice
;; - then add again all the repeated sequences for divisors that are
;;   the product of three of those prime numbers, as they will have
;;   been added the times, but substracted another three times in the
;;   last step
;; - And so on and so on, alternating sums and substractions. Somehow
;;   it works, although I haven't proved it. For the forth level for
;;   example, we add 4 times in the first step, substract 6 in the
;;   second, add 4 in the third, and substract 1 in the latest, which
;;   gets a total of 1 agin. Basically, it works if
;;   sumk((-1)^(k+1)*(N| k)) = 1, where (N | k) is the binomial
;;   coefficient of N over k, which seems to be true:
;;   https://proofwiki.org/wiki/Alternating_Sum_and_Difference_of_Binomial_Coefficients_for_Given_n

(defn build-factors [factors n]
  "Gets the list of divisors that are the product of n number of prime
  factors given in the list"
  (let [f (first factors)
        r (next factors)]
    (cond
      (not f) '()
      (= n 0) '(1)
      (and (not r) (= 1 n)) (list f)
      :else
      (concat
       (map #(* f %) (build-factors r (- n 1)))
       (build-factors r n)))))

(defn problem2-pt2-all-levels [beg end m factors level]
  (if (> level (count factors))
    0
    (+
     (let [new-factors (build-factors factors level)
           sum (problem2-pt2-single-level beg end m new-factors)]
       (if (= (rem level 2) 0)
         (- sum)
         sum))
     (problem2-pt2-all-levels beg end m factors (+ level 1)))))

(defn problem2-pt2-single-range [beg end]
  (let [m (log10 beg)
        p (log10 end)]
    (if (not= m p)
      (+
       (problem2-pt2-single-range beg (- (*exp10 1 m) 1))
       (problem2-pt2-single-range (+ (*exp10 1 m) 1) end))
      (problem2-pt2-all-levels beg end m (factorize m) 1))))


(defn problem2-pt2 [ranges acc]
  (let [range (first ranges)]
    (if (not range)
      acc
      (let [
            interval (clojure.string/split range #"-")
            beg (Long/parseLong (first interval))
            end (Long/parseLong (first (rest interval)))]
        (recur
         (rest ranges)
         (+
          acc
          (problem2-pt2-single-range beg end)))))))



(with-open [r (clojure.java.io/reader "/tmp/input")]
  (case (nth *command-line-args* 1)
    "1" (println (problem2-pt1 (clojure.string/split (first (line-seq r)) #",") 0))
    "2" (println (problem2-pt2 (clojure.string/split (first (line-seq r)) #",") 0))))
