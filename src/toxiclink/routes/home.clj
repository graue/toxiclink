(ns toxiclink.routes.home
  (:use compojure.core)
  (:require [toxiclink.views.layout :as layout]
            [toxiclink.util :as util]
            [ring.util.response :refer [redirect-after-post]]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defn shorten-url [url]
  (comment do-something-here)
  (redirect-after-post "/")) ;; todo: make this go to url info page

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (POST "/shorten" [url] (shorten-url url)))
