(ns phosphor.legacy
  (:require [phosphor.icons :as phosphor]))

(def ^:no-doc old-defaults {:display "inline-block"
                            :line-height "1"})

(defn render [id & [{:keys [size color style] :as attrs}]]
  (phosphor/render id (update attrs :style #(merge old-defaults %))))

(defn load-icon! [id resource])

(defmacro ^:export icon [id]
  (if (:ns &env)
    ;; ClojureScript needs to load the icon into the build
    `(do
       (phosphor.icons/load-icon! ~id ~(phosphor/load-icon-resource* id))
       ~id)
    ;; Clojure loads icons lazily when rendering
    id))

(defmacro get-icon-ids []
  (phosphor/get-icon-ids*))

(defmacro load-all-icons []
  (apply list
         'do
         (for [id (phosphor.icons/get-icon-ids*)]
           (list 'phosphor.legacy/icon id))))
