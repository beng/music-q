(ns music-q.posts
  (:refer-clojure)
  (:require [clojure.java.jdbc :as j]))

; need the stringtype for querying by id
(def postgres-db {:subprotocol "postgresql"
                  :subname "//localhost:5432/concourse"
                  :stringtype "unspecified"})

(defn query-all[]
  (j/query postgres-db ["select * from music_q"]))

(defn query-by-id [id]
  (first (j/query postgres-db
                  ["select * from music_q where id=?" id])))

(defn create [params]
  (j/insert! postgres-db :music_q params))

(defn save [id params]
  (j/update! postgres-db :music_q params ["id=?" id]))