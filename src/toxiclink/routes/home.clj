(ns toxiclink.routes.home
  (:use compojure.core)
  (:require [toxiclink.views.layout :as layout]
            [toxiclink.util :as util]
            [toxiclink.models.db :as db]
            [ring.util.response :refer [redirect-after-post]]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defn shorten-url [url]
  (let [saved-id (db/save-link [url])]
    ;; TODO: redirect to a full confirmation page with the short link.
    ;;       (sf 2013-11)
    (str "We saved the link and got this back: " saved-id)))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (POST "/shorten" [url] (shorten-url url)))
