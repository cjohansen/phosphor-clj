(ns phosphor.icons
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def base-path "phosphor-icons/2.0.0")

(defn get-icon-path [id]
  (str base-path "/"
       (str/replace (namespace id) #"^phosphor\." "") "/"
       (name id) ".edn"))

(defn load-icon-resource* [id]
  (-> (get-icon-path id)
      io/resource
      slurp
      read-string))

(def load-icon-resource (memoize load-icon-resource*))

(defn render [id & [{:keys [size color style] :as attrs}]]
  (if-let [svg (load-icon-resource id)]
    (-> svg
        (assoc-in [1 :style] (cond-> {:display "inline-block"
                                      :line-height "1"}
                               size (assoc :height size)
                               size (assoc :width size)
                               color (assoc :color color)
                               style (into style)))
        (update 1 merge (dissoc attrs :size :color :style)))
    (throw (Error. (str "Icon " id " does not exist")))))

(defn load-icon! [id resource])

(defmacro ^:export icon [id]
  (if (:ns &env)
    ;; ClojureScript needs to load the icon into the build
    `(do
       (phosphor.icons/load-icon! ~id ~(load-icon-resource* id))
       ~id)
    ;; Clojure loads icons lazily when rendering
    id))

(defn get-icon-ids* []
  (->> (io/resource base-path)
       io/file
       file-seq
       (map str)
       (filter #(re-find #"\.edn$" %))
       (map #(second (str/split % (re-pattern (str base-path "/")))))
       (map #(let [[style id] (str/split % #"/")]
               (keyword (str "phosphor." style) (str/replace id #"\.edn$" ""))))
       sort
       vec))

(defmacro get-icon-ids []
  (get-icon-ids*))

(defmacro load-all-icons []
  (apply list
         'do
         (for [id (get-icon-ids*)]
           (list 'phosphor.icons/icon id))))
