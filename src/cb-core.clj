(ns db-core
  (:require [monger.core :as mg] [monger.collection :as mc] [monger.operators :refer :all])
  (:import [com.mongodb MongoOptions ServerAddress]))

(load-file "src/private/connection-string.clj")


(defn mongo-connect []
  (let [uri constants/connection-string
       {:keys [conn db]} (mg/connect-via-uri uri)]
       db))
