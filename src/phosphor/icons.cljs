(ns phosphor.icons
  (:require-macros [phosphor.icons]))

(def icons (atom {}))

(defn load-icon! [id hiccup]
  (swap! icons assoc id hiccup))

(defn get-loaded-icons []
  (keys @icons))

(defn render [id & [{:keys [size color style]}]]
  (if-let [svg (get @icons id)]
    (assoc-in svg [1 :style] (cond-> {:display "inline-block"
                                      :line-height "1"}
                               size (assoc :height size)
                               size (assoc :width size)
                               color (assoc :color color)
                               style (into style)))
    (throw (js/Error. (str "Icon " id " is not loaded. Try loading it with `load-icon!`, or check that it exists.")))))
