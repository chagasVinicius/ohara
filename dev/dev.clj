(ns dev
  (:require [nextjournal.clerk :as clerk]))

(comment
  (clerk/serve! {:watch-paths ["notebooks" "src"]})
  #_(clerk/show! "notebooks/NOME.clj")
  )
