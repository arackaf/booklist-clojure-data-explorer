(ns users.user-data
  (:gen-class
    :name booklist.util
    :methods [#^{:static true} [userBookUsage [] "[Ljava.lang.Object;"]])
  (:require [monger.core :as mg] [monger.collection :as mc] [monger.operators :refer :all])
  (:import [com.mongodb MongoOptions ServerAddress]))


(load-file "src/constants.clj")
(load-file "src/db_core.clj")


(defn normalize-db-object [obj]
  (assoc obj :_id (str (:_id obj))))

(defn active-users []
  (let [db (db-core/mongo-connect)]
       (map normalize-db-object (mc/find-maps db "users" {:activated true} {:email 1 :activated 1 :isPublic 1 :_id 1}))))

(defn users-book-counts []
  (let [db (db-core/mongo-connect)]
        (map normalize-db-object (mc/aggregate db "books" [
          {$group {:_id "$userId" :count { $sum 1 }}}
        ]))))

(defn -userBookUsage []
  (let [active-users (future(active-users))
        user-counts (future (users-book-counts))
        user-count-lookup (reduce (fn [hash user-obj] (assoc hash (:_id user-obj) (:count user-obj))) {} @user-counts)]
    (map (fn [user] (assoc user :count (get user-count-lookup (:_id user) 0))) @active-users)))

(-userBookUsage)



;(doseq [user (active-users)]  (prn (:_id user)))

(defn user-book-count-lookup []
  (let [user-counts (users-book-counts)]
    (reduce (fn [hash user-obj] (assoc hash (keyword (:_id user-obj)) (:count user-obj))) {} user-counts)))


(doseq [grouping (users-book-counts)] (prn grouping))

;(let [map (user-book-count-lookup)]
;  (print "\n\n")
;  (print "Results:")
;  (print (:58aaa7cfe0fbfbef000ab7ec map))
;  (print (:589ac6158e4394f60026d452 map))
;)

