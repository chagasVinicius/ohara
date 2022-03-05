(ns ohara.preprocess.io
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

;; TODO this function could be more generic, maybe using the 'count-type' as input
(defn- third-entry-by-line [[row-label & line]]
  (loop [[_first _second third & ls] line
         result [row-label]]
    (if (nil? third)
      result
      (recur ls (conj result third)))))

(defn raw-tcga->samples+genes-counts-RPKM [file-path]
  (->> (line-seq (io/reader file-path))
       (sequence (comp (map #(str/split % #"\t"))
                       (map third-entry-by-line)))))
