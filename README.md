# Phosphor icons as Hiccup

This library provides all the lovely icons in the open source [Phosphor Icons
package](https://phosphoricons.com/) as hiccup, usable from both Clojure and
ClojureScript.

## Install

With tools.deps:

```clj
no.cjohansen/phosphor-clj {:mvn/version "2023.04.27"}
```

With Leiningen:

```clj
[no.cjohansen/phosphor-clj "2023.04.27"]
```

## Usage from Clojure

Usage from Clojure is straight forward:

```clj
(require '[phosphor.icons :as icons])

(icons/render :phosphor.regular/x)

;;=> [:svg
;;     {:xmlns "http://www.w3.org/2000/svg",
;;      :viewBox "0 0 256 256",
;;      :style {:line-height "1", :display "inline-block"}}
;;     [:rect {:width "256", :height "256", :fill "none"}]
;;     [:line
;;      {:x1 "200",
;;       :y1 "56",
;;       :x2 "56",
;;       :y2 "200",
;;       :stroke "currentColor",
;;       :stroke-linecap "round",
;;       :stroke-linejoin "round",
;;       :stroke-width "16"}]
;;     [:line
;;      {:x1 "200",
;;       :y1 "200",
;;       :x2 "56",
;;       :y2 "56",
;;       :stroke "currentColor",
;;       :stroke-linecap "round",
;;       :stroke-linejoin "round",
;;       :stroke-width "16"}]]
```

More on the `render` function below.

## Usage from ClojureScript

The Phosphor icons package contains thousands of icons. You probably do not want
to include all of them in your build. To work around this, the library provides
a macro that "installs" an icon into your build. After you've installed it, you
can render it as much as you want.

```clj
(require '[phosphor.icons :as icons])

(icons/render (icons/icon :phosphor.regular/apple-logo))
```

This will both pull the Apple logo icon into your build and render it.
`icons/icon` needs only be called once per unique id. It returns the keyword, so
can be used where you build data - it doesn't have to sit in your rendering
code:

```clj
(def data
  {:name "Christian"
   :icon (icons/icon :phosphor.regular/person)})

(icons/render (:icon data) {:size 32})
```

More on the `render` function below.

You can also install icons in a separate namespace and forget about `icons/icon`
in the rest of your application. The important part is that
`phosphor.icons/icon` is called once for every icon you intend to use, and that
it is called with the static keyword - it is a macro, and cannot dynamically
look up refs. This will **not** work:

```clj
;; Doesn't work, don't do it!

(for [id [:phosphor.regular/person
          :phosphor.fill/person
          :phosphor.regular/user
          :phosphor.fill/user]]
  (phosphor.icons/icon id))
```

## Icon keywords

Icons are identified with a keyword. The keyword has the following anatomy:

```clj
:phosphor.<style>/<id>
```

`style` is one of:

- `bold`
- `duotone`
- `fill`
- `light`
- `regular`
- `thin`

`id` is the icons id. Use the [Phosphor icons
website](https://phosphoricons.com/) to find icons.

## The render function

The render function takes two arguments:

```clj
(render id {:size :color :style :class})
```

All the map options are optional.

- `size` is a number that is used for the icons width and height
- `color` is the icon's color. Icons use `currentColor`, so you can also set
  color with CSS in parent elements.
- `style` is a map of styles for the `svg` element
- `class` is either a compatible format for specifying CSS classes that your
  rendering library supports (usually either an array of strings or a
  space-separated list)

The remaining map is merged into the SVG element's attributes, e.g.:

```clj
(require '[phosphor.icons :as icons])

(icons/render (icons/icon :phosphor.regular/x) {:on-click (fn [e] ,,,)})

;;=> [:svg {:on-click (fn [e] ,,,)
;;          :viewBox ",,,"
;;          ,,,}
;;    ,,,]
```

## License

Copyright code in this repo © 2023 Christian Johansen

Distributed under the MIT license.

Phosphor icons also use the MIT license, see [their
Github](https://github.com/phosphor-icons/homepage).
