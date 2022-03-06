;; # Preprocess notebook
(ns preprocess
  (:require
   [nextjournal.clerk :as clerk]
   [ohara.preprocess.io :as preprocess.io]
   [ohara.preprocess.logic :as preprocess.logic]))

;; This is the first notebook that intends to show the Ohara functionalities to works with the TCGA transcription (RNAseq/miRNAseq) data.

;; ## Acquiring data
;; You can download the data-set used in this example in [TCGA](http://firebrowse.org/) > BRCA > RNA-seq_expression_data
;;
;; ## Preprocess
;; In this study we will use only the "RPKM" gene counts for each sample.

^{::clerk/visibility :show}
(def breast-table
  (-> (preprocess.io/raw-tcga->samples+genes-counts-RPKM "./resources/breast_2022.txt")
      preprocess.logic/map-entries))

(clerk/table breast-table)
;; I think it would be nice to visualize all table with row (gene) names, just to help in the "data check".

;; Here is an example of plotting data. We use the entire FOX gene family.
(defn- map-entries-by-sample [acc gene-counts-by-sample]
  (let [samples (keys gene-counts-by-sample)
                  counts (vals gene-counts-by-sample)
                  gene (:gene gene-counts-by-sample)
                  entry->map (fn [sample a-count]
                               {:gene gene
                                :sample sample
                                :count a-count})]
              (into acc (map entry->map samples counts))))

(let [fox-gene-counts (take 3 (filter #(re-matches #"FOX.*" (:gene %)) breast-table))
      data-to-plot (reduce map-entries-by-sample [] fox-gene-counts)]
  (clerk/vl
   {:data {:values data-to-plot}
    :width 700
    :height 500
    :mark {:type :point}
    :encoding {:x {:value :sample
                   :type :qualitative}
               :y {:value :count
                   :type :quantitative}
               :color {:field :gene :type :nominal}}}))
