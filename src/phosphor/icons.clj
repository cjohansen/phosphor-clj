(ns phosphor.icons
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hickory.core :as hiccup]))

(def base-path "phosphor-icons/2.0.0")

(defmacro icon [id]
  `(do
     (phosphor.icons/load-icon!
      ~id
      ~(-> (str base-path "/"
                (str/replace (namespace id) #"^phosphor\." "") "/"
                (name id) ".edn")
           io/resource
           slurp
           read-string))
     ~id))

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

(defn to-hiccup [markup]
  (-> markup
      hiccup/parse
      hiccup/as-hiccup
      first
      last
      last
      (update-in [1] #(-> % (dissoc :viewbox) (assoc :viewBox (:viewbox %))))))

(defn ensure-dir! [^String dir]
  (.mkdirs (io/file dir)))

(defn ensure-parent-dir! [^String path]
  (ensure-dir! (.getParent (io/file path))))

(defn convert-icon [base-dir out-dir path]
  (let [target (str out-dir "/" (str/replace path #"\.svg$" ".edn"))
        hiccup (cond-> (to-hiccup (slurp (str base-dir "/" path)))
                 (re-find #"fill\/" path) (assoc-in [1 :fill] "currentColor"))]
    (ensure-parent-dir! target)
    (spit target (str/replace (pr-str hiccup) #":opacity \"0.2\"" ":opacity \"0.2\" :fill \"currentColor\""))))

(defn convert-icons [base-dir out-dir]
  (doseq [file (->> (file-seq (io/file base-dir))
                    (map #(.getPath %))
                    (filter #(re-find #"\.svg$" %))
                    (map #(str/replace % (str base-dir "/") "")))]
    (convert-icon base-dir out-dir file)))

(defn -main [base-dir]
  (convert-icons base-dir (str "resources/" base-path)))
