(ns phosphor.icons
  (:require-macros [phosphor.icons]))

(def icons (atom {}))

(defn load-icon! [id hiccup]
  (swap! icons assoc id hiccup))

(defn get-loaded-icons []
  (keys @icons))

(defn render [id & [{:keys [size color style] :as attrs}]]
  (if-let [svg (get @icons id)]
    (let [color (or color "currentColor")]
      (-> svg
          (assoc-in [1 :style] (cond-> {}
                                 color (assoc :color color)
                                 color (assoc :fill color)
                                 size (assoc :height size)
                                 size (assoc :width size)
                                 style (into style)))
          (update 1 merge (dissoc attrs :size :color :style))))
    (throw (js/Error. (str "Icon " id " is not loaded. Try loading it with `load-icon!`, or check that it exists.")))))
