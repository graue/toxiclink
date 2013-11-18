(ns toxiclink.util
  (:require [noir.io :as io]
            [markdown.core :as md]))

(def hostname
  (get (System/getenv) "HOSTNAME" "localhost:3000"))

(def protocol
  (get (System/getenv) "PROTOCOL" "http"))

(defn full-link
  "Given a pathname (including the starting /), returns full URL."
  [path]
  (str protocol "://" hostname path))

(defn md->html
  "reads a markdown file from public/md and returns an HTML string"
  [filename]
  (->>
    (io/slurp-resource filename)
    (md/md-to-html-string)))

(defn id->token
  "Converts a numeric ID to the appropriate link token (string)."
  [id]
  (Long/toString id 36))

(defn token->id
  "Inverse of id->token."
  [token]
  (Long/parseLong token 36))

(def token-regex #"[0-9a-zA-Z]+")
