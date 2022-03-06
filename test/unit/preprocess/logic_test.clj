(ns unit.preprocess.logic-test
  (:require [clojure.test :refer [deftest is testing]]
            [ohara.preprocess.logic :as preprocess.logic]))

(def entry-mock
  [["Hybridization REF" "TCGA-A1-A0SB-01A-11R-A144-07" "TCGA-A1-A0SD-01A-11R-A115-07"]
   ["gene" "RPKM" "RPKM"]
   ["?|111" "0" "0"]
   ["?|222" "1.19146690937079" "1.19146690937079"]
   ["?|Blob" "0" "0"]])


(deftest map-entries-test
  (testing "Should return a collection of maps samplesXgene-count and the gene identification"
    (is (= [{:TCGA-A1-A0SB-01A-11R-A144-07 "0",
             :TCGA-A1-A0SD-01A-11R-A115-07 "0",
             :gene "?|111"}
            {:TCGA-A1-A0SB-01A-11R-A144-07 "1.19146690937079",
             :TCGA-A1-A0SD-01A-11R-A115-07 "1.19146690937079",
             :gene "?|222"}
            {:TCGA-A1-A0SB-01A-11R-A144-07 "0",
             :TCGA-A1-A0SD-01A-11R-A115-07 "0",
             :gene "?|Blob"}]
           (preprocess.logic/map-entries entry-mock))))
  (testing "Should return empty collection"
    (is (= []
           (preprocess.logic/map-entries [])))))
