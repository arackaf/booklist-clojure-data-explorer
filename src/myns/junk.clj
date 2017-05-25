(ns myns.junk
  (:gen-class
    :name booklist.util
    :methods [#^{:static true} [sq [int] int]]))

(defn -sq [x] (* x x))

(-sq 7)
