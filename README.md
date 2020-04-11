# Darkstar

Darkstar packages Vega `5.10.1` and Vega-lite `4.10.1` as a single
dependency Clojure library with a very small API surface.

This was made relatively easy by the GraalJS Javascript runtime, which
should work on any stock JVM >= `1.8.0_131`. I have tested it on that
version of HotSpot, as well as OpenJDK 11 and 13.

## Installation

We have not yet released to Clojars, so we recommended you use deps.edn:

``` clojure
applied-science/darkstar {:git/url "https://github.com/applied-science/darkstar/"
                          :sha "541a3ff36065c59e92fe6aa61e41a4385ba6f893"}
```

## Usage

``` clojure
(ns test
  (:require [applied-science.darkstar :as darkstar]))

;; write an SVG from a Vega spec
(->> (slurp "vega-example.json")
     darkstar/vega-spec->svg
     (spit "vg-example.svg"))

;; write an SVG from a Vega-lite spec
(->> (slurp "vega-lite-example.json")
     darkstar/vega-lite-spec->svg
     (spit "vl-example.svg"))
```

## Development 

Build a deployable jar of this library:

    $ clojure -A:jar

Install it locally:

    $ clojure -A:install

Deploy it to Clojars -- needs `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` environment variables:

    $ clojure -A:deploy

## License

Copyright © 2020 Applied Science

Distributed under the MIT License.
