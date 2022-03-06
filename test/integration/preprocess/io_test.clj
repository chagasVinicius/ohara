(ns integration.preprocess.io-test
  (:require [clojure.test :refer [deftest is]]
            [ohara.preprocess.io :as preprocess.io]))

(deftest raw-tcga->samples+genes-counts-RPKM-test
  (is (= [["Hybridization REF" "TCGA-A1-A0SB-01A-11R-A144-07" "TCGA-A1-A0SD-01A-11R-A115-07"]
          ["gene" "RPKM" "RPKM"]
          ["?|100130426" "0" "0"]
          ["?|100133144" "1.19146690937079" "1.19146690937079"]
          ["?|100133144" "0" "0"]]
         (preprocess.io/raw-tcga->samples+genes-counts-RPKM "./resources/test/mock_breast.txt"))))
