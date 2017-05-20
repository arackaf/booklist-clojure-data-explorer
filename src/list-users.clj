(ns my.service.server
  (:require [monger.core :as mg] [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))


(load-file "src/private/connection-string.clj")


(defn active-users
  []
  (let [uri constants/connection-string
       {:keys [conn db]} (mg/connect-via-uri uri)
       coll "users"]
       (mc/find-maps db coll {} {:email 1 :activated 1 :isPublic 1 :_id 0})))

(doseq [book (active-users)] (prn book))
