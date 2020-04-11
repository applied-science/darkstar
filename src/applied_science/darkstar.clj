(ns applied-science.darkstar)

(def engine
  (let [engine (.getEngineByName (javax.script.ScriptEngineManager.) "graal.js")
        bindings (.getBindings engine javax.script.ScriptContext/ENGINE_SCOPE)]
    (.put bindings "polyglot.js.allowAllAccess" true)
    (doto engine
      ;; XXX minimal polyfill for part of the fetch and fs APIs, brittle af
      (.eval "
async function fetch(path, options) {
  print(Object.keys(options));
  var body = Java.type('clojure.core$slurp').invokeStatic(path,null);
  return {'ok' : true,
          'body' : body,
          'text' : (function() {return body;}),
          'json' : (function() {return JSON.parse(body);})};
}
function readFile(path, callback) {
  var data = Java.type('clojure.core$slurp').invokeStatic(path,null);
  callback(null, data);
}
var fs = {'readFile':readFile};
")
      (.eval (slurp (clojure.java.io/resource "vega.js")))
      (.eval (slurp (clojure.java.io/resource "vega-lite.js"))))))

(defn make-js-fn [js-text]
  (let [^java.util.function.Function f (.eval engine js-text)]
    (fn [& args] (.apply f (to-array args)))))

(def vega-lite->vega
  (make-js-fn "function(vlSpec) { return JSON.stringify(vegaLite.compile(JSON.parse(vlSpec)).spec);}"))

(def vega-spec->view
  (make-js-fn "function(spec) { return new vega.View(vega.parse(JSON.parse(spec)), {renderer:'svg'}).finalize();}"))

(def view->svg
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

;; Polyglot.export(key, value)
;; Polyglot.import(key, value)
;; Polyglot.eval(languageId, sourceCode)
;; Polyglot.evalFile(languageId, sourceFileName)

;;(System/getProperties)
;;  System.setProperty("polyglot.js.ecmascript-version", "2020");
;;js.syntax-extensions
