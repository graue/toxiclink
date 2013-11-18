(ns toxiclink.routes.home
  (:use compojure.core)
  (:require [toxiclink.views.layout :as layout]
            [toxiclink.util :as util]
            [toxiclink.models.db :as db]
            [ring.util.response :refer [redirect redirect-after-post]]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defn shorten-url [url]
  (let [saved-id (db/save-link [url])]
    (redirect-after-post (str "/confirm/" (util/id->token saved-id)))))

(defn success-page [token]
  (let [link (db/get-link (util/token->id token))]
    (layout/render "success.html"
                   {:input-link (:link link)
                    :short-link (util/full-link (str "/" token))})))

(defn redirect-to [token]
  (let [link (db/get-link (util/token->id token))]
    (redirect (:link link))))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (POST "/shorten" [url] (shorten-url url))
  (GET ["/confirm/:token", :token util/token-regex] [token]
       (success-page token))
  (GET ["/:token", :token util/token-regex] [token] (redirect-to token)))
