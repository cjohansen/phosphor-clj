(ns phosphor.icons
  (:require [clojure.string :as str]
            [hickory.core :as hiccup])
  (:import (java.io File)))

(defn to-hiccup [markup]
  (-> markup
      hiccup/parse
      hiccup/as-hiccup
      first
      last
      last
      (update-in [1] #(-> % (dissoc :viewbox) (assoc :viewBox (:viewbox %))))))

(defn ensure-dir! [^String dir]
  (.mkdirs (File. dir)))

(defn ensure-parent-dir! [^String path]
  (ensure-dir! (.getParent (File. path))))

(defn convert-icon [base-dir out-dir path]
  (let [target (str out-dir "/" (str/replace path #"\.svg$" ".edn"))
        hiccup (to-hiccup (slurp (str base-dir "/" path)))]
    (ensure-parent-dir! target)
    (spit target (pr-str hiccup))))

(defn convert-icons [base-dir out-dir]
  (doseq [file (->> (file-seq (File. base-dir))
                    (map #(.getPath %))
                    (filter #(re-find #"\.svg$" %))
                    (map #(str/replace % (str base-dir "/") "")))]
    (convert-icon base-dir out-dir file)))

(defn -main [base-dir]
  (convert-icons base-dir "resources/phosphor-icons/2.0.0"))
