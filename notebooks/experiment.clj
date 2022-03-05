(ns experiment
  (:require
   [nextjournal.clerk :as clerk]
   [ohara.preprocess :as preprocess]))

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
  (let [[samples counts-type & genes-counts] (preprocess/raw-tcga->samples+genes-counts-RPKM "./resources/breast.txt")]
    (map-entries samples genes-counts counts-type)))

(clerk/table breast-table)
