(ns toxiclink.models.db
  (:require [toxiclink.models.schema :as schema]
            [korma.core :refer :all]
            [korma.db :refer [defdb transaction]]))

(defdb db schema/db-spec)

(defentity urls)

(defn get-last-id
  "Gets ID of last inserted item. Should be called within a transaction after
  the insert."
  [table]
  ;; FIXME: This method only works with H2. Postgres has a different way of
  ;;        doing it. (sf 2013-11)
  (-> (select urls
              (fields [(raw "IDENTITY()") :id])
              (limit 1)) ; FIXME: Don't know why needed. (sf 2013-11)
                         ; H2 returns same ID multiple times, as many
                         ; occurrences as there are entries in the table. ???
      (first)
      (:id)))

(defn save-link
  "Save a link to the given long URL and return its numeric ID."
  [long-url]
  (transaction
    (insert urls
            (values {:link long-url
                     :timestamp (new java.util.Date)}))
    (get-last-id urls)))

(defn get-link [id]
  (first (select urls
                 (where {:id id})
                 (limit 1))))
