(ns applied-science.darkstar-test
  (:require [clojure.test :refer :all]
            [clojure.xml :as xml]
            [applied-science.darkstar :refer :all]))

(def md5
  (let [hasher (java.security.MessageDigest/getInstance "MD5")]
    (fn [string]
      (javax.xml.bind.DatatypeConverter/printHexBinary
       (.digest hasher (.getBytes string "UTF-8"))))))

(defn spec-file->md5 [filename]
  (->> (read-file filename)
       vega-spec->svg
       md5))

;; these work from inside emacs, fail at the command line. Seems to be
;; a problem looking up the read-file function from the JS side when
;; testing, maybe needs AOT?
(deftest smoke-test
  (binding [*base-directory* "test/applied_science/vega-specs/"]
    (testing "Comparing output to known good values..."
      (is (= (spec-file->md5 "airports.vg.json") "303C629B80C64E00B86F0819CB50CD81"))
      (is (= (spec-file->md5 "arc-diagram.vg.json") "51E078656D822022523E6FC76D343030"))
      (is (= (spec-file->md5 "arc.vg.json") "B448933BCC50D476064629D73EBAC4DD"))
      (is (= (spec-file->md5 "area.vg.json") "6D14074345E61ADF07ACC1FCF338224C"))
      (is (= (spec-file->md5 "autosize-fit-x.vg.json") "77A69E6723E990249DB37EA44828F559"))
      (is (= (spec-file->md5 "autosize-fit-y.vg.json") "9F95F4D33EECD4BEAED654B43CAE39BA"))
      (is (= (spec-file->md5 "autosize-fit.vg.json") "B8CF22BE485A5FBDD529CE84E285C839"))
      (is (= (spec-file->md5 "bar-hover-label.vg.json") "BF3E3CDEF81F795F042E052F390EC170"))
      (is (= (spec-file->md5 "bar-rangestep.vg.json") "5FD4208444946847511C34029554D9DF"))
      (is (= (spec-file->md5 "bar-time.vg.json") "10217A655B7D0ABD1729DDB1C82A39DD"))
      (is (= (spec-file->md5 "bar.vg.json") "774988F1957F972BF3EB8DDEB72658D5"))
      (is (= (spec-file->md5 "barley.vg.json") "C10B643D18CBE96038EFB25F4C88F092"))
      (is (= (spec-file->md5 "budget-forecasts.vg.json") "20725287677E0DE91F5D582CC4B0A556"))
      (is (= (spec-file->md5 "chart-rangestep.vg.json") "3527261839D59779237486351D7E141F"))
      (is (= (spec-file->md5 "chart.vg.json") "940B4C390C934DAF62947F64DF05005C"))
      (is (= (spec-file->md5 "choropleth.vg.json") "BBC427A9B1550E0D4C210559D5C31C97"))
      (is (= (spec-file->md5 "corner-radius.vg.json") "1804E1B20018A4B6928779DECF2122BD"))
      (is (= (spec-file->md5 "crossfilter-multi.vg.json") "316CFE212220DCC8BD773A6A8596A938"))
      (is (= (spec-file->md5 "crossfilter.vg.json") "AD3BFC76DAC0D0E2D723F626F05F487A"))
      (is (= (spec-file->md5 "density.vg.json") "9A9E982D0E18CF975CDDFA29BECBB515"))
      (is (= (spec-file->md5 "dimpvis.vg.json") "09203E91368133E60D6D47A7308DB385"))
      (is (= (spec-file->md5 "dot-plot.vg.json") "E7B444FD540B8270569318A55732319F"))
      (is (= (spec-file->md5 "driving.vg.json") "F57E814C25DB50BDA46849C6B971C118"))
      (is (= (spec-file->md5 "dynamic-format.vg.json") "B3F6CADDFB90BDF967964CCD08B50797"))
      (is (= (spec-file->md5 "dynamic-url.vg.json") "6E7EE789CFB4663C32ED6E5BB4BBDFD6"))
      (is (= (spec-file->md5 "error.vg.json") "810C69CB7A17424E0617901AA2196052"))
      (is (= (spec-file->md5 "falkensee.vg.json") "B1BB2C442D5365E72ACE1A3C49C9B4F7"))
      (is (= (spec-file->md5 "flush-axis-labels.vg.json") "AED55ADFE4E9178CEEFD1B556887C94D"))
      (is (= (spec-file->md5 "font-size-steps.vg.json") "D93D97C5EE40E7528867F8AB2C51FE86"))
      (is (= (spec-file->md5 "grouped-bar.vg.json") "8FFE17C62B96A637CF138CAF40F6F393"))
      (is (= (spec-file->md5 "heatmap-lines.vg.json") "8D0F895EF555B14087FA544BF588B16A"))
      (is (= (spec-file->md5 "histogram.vg.json") "3BEF88816C2C6FC18147014E68C68710"))
      (is (= (spec-file->md5 "images.vg.json") "68C8AF8D70EC227DA77B4272812ED8C9"))
      (is (= (spec-file->md5 "jobs.vg.json") "BB9FA7EDFB5944C9AD52D740862324D8"))
      (is (= (spec-file->md5 "kde.vg.json") "48211F9A9A60EA1CADBA49F050DB4853"))
      (is (= (spec-file->md5 "layout-facet.vg.json") "6A62C0C1AACE88F9AEFBECA488E2CFCC"))
      (is (= (spec-file->md5 "layout-hconcat.vg.json") "A4E4546A4780EF316763CC85EC2825F0"))
      (is (= (spec-file->md5 "layout-splom.vg.json") "87AB7B61D8F820F095A13CC9959DFF56"))
      (is (= (spec-file->md5 "layout-vconcat.vg.json") "7BAFC4CD4D055AB5FBCC6ED68C74F826"))
      (is (= (spec-file->md5 "layout-wrap.vg.json") "9894433F62093DC5EC6807F2B1762640"))
      (is (= (spec-file->md5 "legends-discrete.vg.json") "1D5C1A53FF29B3A284A6DB26569CA046"))    
      (is (= (spec-file->md5 "legends-ordinal.vg.json") "04161E1D46F07ECEED5AA7607FDEBB4F"))
      (is (= (spec-file->md5 "lifelines.vg.json") "AF7D691E005DEEF3A84EAF2741C5A5CB"))
      (is (= (spec-file->md5 "map-area-compare.vg.json") "DDA3CEF9FE0F087ECC19B94C6467AD35"))
      (is (= (spec-file->md5 "map-bind.vg.json") "D1CEB299935BB88505063932DCF2E2CC"))
      (is (= (spec-file->md5 "map-fit.vg.json") "B79507FC170AB5023F8B9B4719624806"))
      (is (= (spec-file->md5 "map-point-radius.vg.json") "4AAFF916DA1BA38201B770C3CC6824FC"))
      (is (= (spec-file->md5 "map.vg.json") "AAC532B579D09C81269CF6045A2BD6F5"))
      (is (= (spec-file->md5 "matrix-reorder.vg.json") "B5CD5DFB3132E7660C9156C91232C7CB"))
      (is (= (spec-file->md5 "movies-sort.vg.json") "A3CA7D532A8FB69D17D78A9DAB0B752F"))
      (is (= (spec-file->md5 "nested-plot.vg.json") "467847A99DE39108E863F0A089D72F31"))
      (is (= (spec-file->md5 "nulls-histogram.vg.json") "CF5910FCBDCB230B018924691309AC75"))
      (is (= (spec-file->md5 "nulls-scatter-plot.vg.json") "79932B9114101222BF5144564D135190"))
      (is (= (spec-file->md5 "panzoom.vg.json") "6D6D690F2AC66C1D3A531FB6722CFBC2"))
      (is (= (spec-file->md5 "parallel-coords.vg.json") "B98D2B2691ACDD0BB847E6544A205697"))
      (is (= (spec-file->md5 "playfair.vg.json") "BE5076A47E0F28228124C201C97ECED6"))
      (is (= (spec-file->md5 "population.vg.json") "368B9DA96436500FEDAC0588C8D7A6A9"))
      (is (= (spec-file->md5 "quantile-dot-plot.vg.json") "0D0785946050773D158C9899E95D2ACE"))
      (is (= (spec-file->md5 "quantile-quantile-plot.vg.json") "58707EEDD7B97BB750AA8764DF9448AE"))
      (is (= (spec-file->md5 "regression.vg.json") "5CAADF7ACE9EF0551DB6576A09A97DC4"))
      (is (= (spec-file->md5 "scales-bin.vg.json") "D7BDD6FD7FC2EFB3F60B80490E5542E6"))
      (is (= (spec-file->md5 "scales-discretize.vg.json") "BB7F66F21C3425AA09CF3463F7A13F0E"))
      (is (= (spec-file->md5 "scatter-plot-guides.vg.json") "B386B20F8000EBEE13009576E3CCB06A"))
      (is (= (spec-file->md5 "scatter-plot.vg.json") "08A108B9C65747AE9EBA05C428021057"))
      (is (= (spec-file->md5 "shift-select.vg.json") "28879C8DD767979EAF3BCA57A42611D8"))
      (is (= (spec-file->md5 "splom-inner.vg.json") "69880A9B6464AD61981CE7BA13CC5432"))
      (is (= (spec-file->md5 "splom-outer.vg.json") "536BD14A77A571DFDA26EFC0581A8E10"))
      (is (= (spec-file->md5 "stacked-area.vg.json") "100ACF72227EEFA89C4B9AB381C079C6"))
      (is (= (spec-file->md5 "stacked-bar.vg.json") "2A013B0669EA635ECF4110BE35482854"))
      (is (= (spec-file->md5 "stocks-index.vg.json") "29537CADC0246C79517595824A37AAD7"))
      (is (= (spec-file->md5 "symbol-angle.vg.json") "AAF053D6658E56089EEDB4FC4030C20B"))
      (is (= (spec-file->md5 "titles.vg.json") "8BA113ACE53E0CE4D76186D6D567A06E"))
      (is (= (spec-file->md5 "tree-cluster.vg.json") "E8BEC2A25AAF100985D0D0870E4C06F3"))
      (is (= (spec-file->md5 "tree-nest.vg.json") "1D0ABD14CF311D26C6027DD03019E122"))
      (is (= (spec-file->md5 "tree-radial-bundle.vg.json") "24441869081D35F02EB6E65D07C8871F"))
      (is (= (spec-file->md5 "tree-radial.vg.json") "3642AFA30287CCB72489714F77350E26"))
      (is (= (spec-file->md5 "treemap.vg.json") "375FF19A304D9EB198C04180A9B5BBC3"))
      (is (= (spec-file->md5 "violin-plot.vg.json") "5B9B6728A498BBAAC67CC49248554D82"))
      (is (= (spec-file->md5 "weather.vg.json") "66963C83571E782A6354FD3DD3FD5849"))
      (is (= (spec-file->md5 "window.vg.json") "69462999260F9BAAC1C90A7D30E7926C")))))

;; TODO these tests all look right to me, but have very slight
;; textual differences between runs that break comparison.
(comment
  (spec-file->md5 "scatter-brush-filter.vg.json")
  (spec-file->md5 "isocontour-airports.vg.json")
  (spec-file->md5 "heatmap.vg.json")
  (spec-file->md5 "horizon.vg.json")
  (spec-file->md5 "contour-map.vg.json")
  (spec-file->md5 "legends-continuous.vg.json")
  (spec-file->md5 "contour-scatter.vg.json")
  (spec-file->md5 "gradient.vg.json")
  (spec-file->md5 "scatter-brush-panzoom.vg.json")
  (spec-file->md5 "overview-detail-bins.vg.json")
  (spec-file->md5 "legends-symbol.vg.json")
  (spec-file->md5 "isocontour-precipitation.vg.json")
  (spec-file->md5 "overview-detail.vg.json")
  (spec-file->md5 "legends.vg.json"))

;; TODO these need to use canvas
(comment
  (spec-file->md5 "heatmap-image.vg.json")
  (spec-file->md5 "heatmap-sinusoids.vg.json")
  (spec-file->md5 "scatter-plot-contours.vg.json")
  (spec-file->md5 "scatter-plot-heatmap.vg.json"))

;; TODO these need setTimeout
(comment
  (spec-file->md5 "force-beeswarm.vg.json")
  (spec-file->md5 "force-network.vg.json"))

;; TODO needs "height" from the browser
(comment
  (spec-file->md5 "isocontour-volcano.vg.json"))
