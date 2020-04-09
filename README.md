# Darkstar

Darkstar packages Vega `5.10.1` and Vega-lite `4.10.1` as a single
dependency Clojure library with a very small API surface.

This was made relatively easy by the GraalJS Javascript runtime, which
should work on any stock JVM >= `1.8.0_131` (the version upon which I
have been testing it).

## Installation

We have not yet released to Clojars, so we recommended you use deps.edn:

``` clojure
applied-science/darkstar {:git/url "https://github.com/appliedsciencestudio/darkstar/"
                          :sha "FIXME"}
```

## Usage

``` clojure
;; write an SVG from a Vega spec
(->> (slurp "vega-example.json")
     vega-spec->svg
     (spit "vg-example.svg"))

;; write an SVG from a Vega-lite spec
(->> (slurp "vega-lite-example.json")
     vega-lite-spec->svg
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
