(ns phosphor.dev
  (:require [phosphor.icons :as icons :refer-macros [get-icon-ids load-all-icons]]
            [portfolio.dumdom :refer-macros [defscene configure-scenes]]
            [portfolio.ui :as ui]))

(configure-scenes
 {:title "Phosphor Icons"})

(load-all-icons)

(defscene bold-icons
  [:div {:style {:display "flex"
                 :flex-wrap "wrap"
                 :gap 20}}
   (->> (get-icon-ids)
        (filter (comp #{"phosphor.bold"} namespace))
        (map #(icons/render % {:size 32 :color "#1181f9"})))])

(defscene duotone-icons
  [:div {:style {:display "flex"
                 :flex-wrap "wrap"
                 :gap 20}}
   (->> (get-icon-ids)
        (filter (comp #{"phosphor.duotone"} namespace))
        (map #(icons/render % {:size 32 :color "red"})))])

(defscene fill-icons
  [:div {:style {:display "flex"
                 :flex-wrap "wrap"
                 :gap 20}}
   (->> (get-icon-ids)
        (filter (comp #{"phosphor.fill"} namespace))
        (map #(icons/render % {:size 32 :color "#61c093"})))])

(defscene light-icons
  [:div {:style {:display "flex"
                 :flex-wrap "wrap"
                 :gap 20}}
   (->> (get-icon-ids)
        (filter (comp #{"phosphor.light"} namespace))
        (map #(icons/render % {:size 32 :color "#1181f9"})))])

(defscene regular-icons
  [:div {:style {:display "flex"
                 :flex-wrap "wrap"
                 :gap 20}}
   (->> (get-icon-ids)
        (filter (comp #{"phosphor.regular"} namespace))
        (map #(icons/render % {:size 32 :color "red"})))])

(defscene thin-icons
  [:div {:style {:display "flex"
                 :flex-wrap "wrap"
                 :gap 20}}
   (->> (get-icon-ids)
        (filter (comp #{"phosphor.thin"} namespace))
        (map #(icons/render % {:size 32 :color "#61c093"})))])

(ui/start!)
