(ns my.service.server
  (:require [monger.core :as mg] [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))


(load-file "src/private/connection-string.clj")


(let [uri constants/connection-string
     {:keys [conn db]} (mg/connect-via-uri uri)
     coll "users"]
     (vec (mc/find-maps db coll {} {:email 1 :activated 1 :isPublic 1 :_id 0})))

