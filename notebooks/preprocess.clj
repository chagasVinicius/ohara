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
  (let [samples (keys (dissoc gene-counts-by-sample :gene))
        counts (vals (dissoc gene-counts-by-sample :gene))
        gene (:gene gene-counts-by-sample)
        entry->map (fn [sample a-count]

                     {:gene gene
                      :sample sample
                      :count (bigdec a-count)})]
    (into acc (map entry->map samples counts))))

(let [gene-counts (filter #(re-matches #"BRCA.*" (:gene %)) breast-table)
      data-to-plot (reduce map-entries-by-sample [] gene-counts)]
  (clerk/vl
   {:data {:values data-to-plot}
    :mark {:type :point}
    :width 700
    :height 700
    :encoding {:x {:field :sample
                   :type :ordinal
                   :axis {:labels false}}
               :y {:field :count
                   :type :quantitative}
               :color {:field :gene :type :nominal}}}))
