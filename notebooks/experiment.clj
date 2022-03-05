(ns experiment
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [nextjournal.clerk :as clerk]))

^{::clerk/visibility :fold}
(defn- third-entry-by-line [[row-label & line]]
  (loop [[_first _second third & ls] line
         result [row-label]]
    (if (nil? third)
      result
      (recur
       ls
       (conj result third)))))
^{::clerk/visibility :fold}
(defn- gene-counts-by-sample-fn [samples gene-counts-by-sample]
  (loop [[sample & samples'] samples
         [gene-count-for-sample & gene-counts-by-sample'] gene-counts-by-sample
         result-gene-for-sample (sorted-map)]
    (if (nil? sample)
      result-gene-for-sample
      (recur
       samples'
       gene-counts-by-sample'
       (assoc result-gene-for-sample (keyword sample) gene-count-for-sample)))))
^{::clerk/visibility :fold}
(defn- map-entries [[_sample-label & samples] genes-counts-by-sample [_discart _count-type & _descart]]
  (lazy-seq (loop [[gene-counts-by-sample & genes-counts-by-sample'] genes-counts-by-sample
                   result []]
              (if (nil? gene-counts-by-sample)
                result
                (let [[gene-name & gene-counts-by-sample'] gene-counts-by-sample]
                  (recur
                   genes-counts-by-sample'
                   (conj result (assoc (gene-counts-by-sample-fn samples gene-counts-by-sample') :gene gene-name))))))))

^{::clerk/visibility :show}
(def breast-table
  (let [[samples counts-type & genes-counts] (->> (line-seq (io/reader "./resources/breast.txt"))
                                                  (sequence (comp (map #(str/split % #"\t"))
                                                                  (map third-entry-by-line))))]
    (map-entries samples genes-counts counts-type)))

(clerk/table breast-table)
