(ns my.service.server
  (:require [monger.core :as mg] [monger.collection :as mc] [monger.operators :refer :all])
  (:import [com.mongodb MongoOptions ServerAddress]))


(load-file "src/private/connection-string.clj")
(load-file "src/cb-core.clj")

(defn active-users []
  (let [db (db-core/mongo-connect)]
       (mc/find-maps db "users" {} {:email 1 :activated 1 :isPublic 1 :_id 0})))

;(doseq [book (active-users)] (prn book))


(defn users-book-counts []
  (let [db (db-core/mongo-connect)]
        (mc/aggregate db "books" [
          {$group {:_id "$userId" :count { $sum 1 }}}
        ])))


(defn user-book-count-lookup []
  (let [user-counts (users-book-counts)]
    (reduce (fn [hash user-obj] (assoc hash (:_id user-obj) (:count user-obj))) {} user-counts)))


;(doseq [grouping (users-book-counts)] (prn grouping))

(let [map (user-book-count-lookup)]
  (print "\n\n")
  (print "Results:")
  (print (get map "58aaa7cfe0fbfbef000ab7ec"))
  (print (get map "589ac6158e4394f60026d452"))
    )

