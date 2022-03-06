(ns ohara.preprocess.logic)

(defn- gene-counts-by-sample-fn [samples gene-counts-by-sample]
  (loop [[sample & samples'] samples
         [gene-count-for-sample & gene-counts-by-sample'] gene-counts-by-sample
         result-gene-for-sample (sorted-map)]
    (if (nil? sample)
      result-gene-for-sample
      (recur
       samples'
       gene-counts-by-sample'
       (assoc result-gene-for-sample (keyword sample) (bigdec gene-count-for-sample))))))

(defn map-entries [[[_sample-label & samples] [_discart _count-type & _descart] & genes-counts-by-sample]]
  (lazy-seq (loop [[gene-counts-by-sample & genes-counts-by-sample'] genes-counts-by-sample
                   result []]
              (if (nil? gene-counts-by-sample)
                result
                (let [[gene-name & gene-counts-by-sample'] gene-counts-by-sample]
                  (recur
                   genes-counts-by-sample'
                   (conj result (assoc (gene-counts-by-sample-fn samples gene-counts-by-sample') :gene gene-name))))))))
