(ns phosphor.legacy-test
  (:require [clojure.test :refer [deftest is testing]]
            [phosphor.legacy :as phosphor]))

(deftest render-test
  (testing "Renders icon with legacy inline styles"
    (is (= (phosphor/render :phosphor.regular/x)
           [:svg {:xmlns "http://www.w3.org/2000/svg"
                  :viewBox "0 0 256 256"
                  :style {:display "inline-block"
                          :line-height "1"}}
            [:rect {:width "256"
                    :height "256"
                    :fill "none"}]
            [:line {:x1 "200"
                    :y1 "56"
                    :x2 "56"
                    :y2 "200"
                    :stroke "currentColor"
                    :stroke-linecap "round"
                    :stroke-linejoin "round"
                    :stroke-width "16"}]
            [:line {:x1 "200"
                    :y1 "200"
                    :x2 "56"
                    :y2 "56"
                    :stroke "currentColor"
                    :stroke-linecap "round"
                    :stroke-linejoin "round"
                    :stroke-width "16"}]]))))
