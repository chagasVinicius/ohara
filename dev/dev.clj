(ns dev
  (:require
   [nextjournal.clerk :as clerk]))

(comment
  (clerk/serve! {:watch-paths ["notebooks" "src"]})
  (clerk/show! "notebooks/experiment.clj"))
