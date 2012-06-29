;; ----------------------------------------------------------------------------
;; Code example for the Bird of a feather @Sfeir 20120615
;; ----------------------------------------------------------------------------

(ns cpug-201206-incanter.core
  (:use     [midje.sweet])
  (:use     [clojure.pprint     :only [pprint pp]])
  (:use     [clojure.string     :only [split join split-lines replace-first] :as s])
  (:use     [clojure.repl       :only [doc find-doc]])
  (:use     [clojure.java.javadoc       :only [javadoc]])
  (:require [clojure.xml        :as xml])
  (:require [clojure.set        :as set])
  (:require [clj-http.client    :as client])
  (:require [clojure.java.shell :as sh])
  (:require [clojure.inspector  :as i])
  (:require [clojure.java.io    :as io])
  (:require [clojure.reflect    :as ref])
  (:use [incanter  core stats charts datasets pdf])
  (:import [java.io File]))



;; listing all files
(def all-files (file-seq (File. "/home/denis/Dropbox/doc-cache")))

;; filter directories
(def no-dir (remove #(.isDirectory %) all-files))

;; Get the extension of the given file
(defn get-extension
  [f] (last (s/split (.getName f) #"\.")))

;; a sequence of extension / size pairs
(def ext-size (map (fn [f] [(get-extension f) (.length f)]) no-dir))

;; group the preceding seq by extension
(def group-by-ext (group-by first ext-size))

;; sum the sizes for each extensions
(def ext-size (map (fn [[e sizes]] [e (reduce + (map second sizes))])
               group-by-ext))

;; view the data
(defn view-pie-chart
  [] (view (pie-chart (map first ext-size)
                      (map second ext-size))))
