(ns toxiclink.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [toxiclink.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity url)

(defn save-link [long-url]
  (insert url
          (values {:link long-url
                   :timestamp (new java.util.Date)})))


(defn get-link [id]
  (first (select url
                 (where {:id id})
                 (limit 1))))
