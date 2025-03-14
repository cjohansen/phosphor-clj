(ns phosphor.legacy
  (:require [phosphor.icons :as phosphor])
  (:require-macros [phosphor.legacy]))

(defn get-loaded-icons []
  (phosphor/get-loaded-icons))

(def ^:no-doc old-defaults {:display "inline-block"
                            :line-height "1"})

(defn render [id & [attrs]]
  (phosphor/render id (update attrs :style #(merge old-defaults %))))
