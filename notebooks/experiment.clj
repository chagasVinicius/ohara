(ns experiment
  (:require [clojure.data.csv :as csv]))

(defn- map-line-info [sample gene-name gene-count]
  {:sample sample
   :gene-name gene-name
   :count gene-count})

#_(def breast-matrix
  (let [data (csv/read-csv (slurp "./resources/breast.txt") :separator \tab)
        all-samples (-> data first rest)
        all-gene-names (map first (rest data))
        all-gene-raw-counts (map #(-> % second bigdec) (nthrest data 2))]
    (loop [[sample & samples] all-samples
           [gene-name & gene-names] all-gene-names
           [gn-count & gn-counts] all-gene-raw-counts
           result []]
      (if (and sample gene-name gn-count)
        (recur
         samples
         gene-names
         gn-counts
         (conj result {:sample sample
                       :gene-name gene-name
                       :count gn-count}))
        result))))

(def breast-matrix
  (let [data' (csv/read-csv (slurp "./resources/breast.txt") :separator \tab)
        ;; all-samples (-> data first rest)
        ;; all-gene-names (map first (rest data))
        ;; all-gene-raw-counts (map #(-> % second bigdec) (nthrest data 2))
        ]
    (-> data' first rest))
  )
