(ns my.service.server
  (:require [monger.core :as mg] [monger.collection :as mc] [monger.operators :refer :all])
  (:import [com.mongodb MongoOptions ServerAddress]))


(load-file "src/private/connection-string.clj")


(defn active-users
  []
  (let [uri constants/connection-string
       {:keys [conn db]} (mg/connect-via-uri uri)
       coll "users"]
       (mc/find-maps db coll {} {:email 1 :activated 1 :isPublic 1 :_id 0})))

;(doseq [book (active-users)] (prn book))


(defn users-book-counts
  []
  (let [uri constants/connection-string
        {:keys [conn, db]} (mg/connect-via-uri uri)
        coll "books"]
        (mc/aggregate db coll [
          {$group {:_id "userId" :count { $sum 1 }}}
        ])))


(doseq [grouping (users-book-counts)] (prn grouping))



