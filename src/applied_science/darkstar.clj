(ns applied-science.darkstar)

(def ^:dynamic *base-directory* nil)

(defn read-file
  "A very, very slight polyfill for Node's fs.readFile that uses `*base-directory*` as Vega's idea of current working directory."
  [filename]
  ;; TODO only system error handling!
  (slurp (.getAbsolutePath (java.io.File. (str *base-directory* filename)))))

(def engine
  (let [engine (.getEngineByName (javax.script.ScriptEngineManager.) "graal.js")
        bindings (.getBindings engine javax.script.ScriptContext/ENGINE_SCOPE)]
    (.put bindings "polyglot.js.allowAllAccess" true)
    (doto engine
      ;; XXX minimal polyfill for part of the fetch and fs APIs, brittle af
      (.eval "
async function fetch(path, options) {
  var body = Java.type('clojure.core$slurp').invokeStatic(path,null);
  return {'ok' : true,
          'body' : body,
          'text' : (function() {return body;}),
          'json' : (function() {return JSON.parse(body);})};
}
function readFile(path, callback) {
  try {
    var data = Java.type('applied_science.darkstar$read_file').invokeStatic(path);
    callback(null, data);
  } catch (err) {
    printErr(err);
  }
}
var fs = {'readFile':readFile};
")
      (.eval (slurp (clojure.java.io/resource "vega.js")))
      (.eval (slurp (clojure.java.io/resource "vega-lite.js"))))))

(defn make-js-fn [js-text]
  (let [^java.util.function.Function f (.eval engine js-text)]
    (fn [& args] (.apply f (to-array args)))))

(def vega-lite->vega
  "Converts a VegaLite spec into a Vega spec."
  (make-js-fn "function(vlSpec) { return JSON.stringify(vegaLite.compile(JSON.parse(vlSpec)).spec);}"))

(def vega-spec->view
  "Converts a Vega spec into a Vega view object, finalizing all resources."
  (make-js-fn "function(spec) { return new vega.View(vega.parse(JSON.parse(spec)), {renderer:'svg'}).finalize();}"))

(def view->svg
  "Converts a Vega view object into an SVG."
  (make-js-fn "function (view) {
    var promise = Java.type('clojure.core$promise').invokeStatic();
    view.toSVG(1.0).then(function(svg) {
        Java.type('clojure.core$deliver').invokeStatic(promise,svg);
    }).catch(function(err) {
        Java.type('clojure.core$deliver').invokeStatic(promise,'<svg><text>error</text></svg>');
    });
    return promise;
}"))

(defn vega-spec->svg
  "Calls Vega to render the spec in `vega-spec-json-string` to the SVG described by that spec."
  [vega-spec-json-string]
  @(view->svg (vega-spec->view vega-spec-json-string)))

(defn vega-lite-spec->svg
  "Converts `vega-lite-spec-json-string` to a full Vega spec, then uses Vega to render the SVG described by that spec."
  [vega-lite-spec-json-string]
  (vega-spec->svg (vega-lite->vega vega-lite-spec-json-string)))

(comment

  (->> (slurp "vega-lite-movies.json")
       vega-lite-spec->svg
       (spit "vl-movies.svg"))
  
  )
