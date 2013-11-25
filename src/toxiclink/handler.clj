(ns toxiclink.handler
  (:require [compojure.core :refer [defroutes]]
            [toxiclink.routes.home :refer [home-routes]]
            [toxiclink.models.schema :as schema]
            [noir.util.middleware :as middleware]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]
            [com.postspectacular.rotor :as rotor]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "Called once when app is deployed as a servlet."
  []
  (timbre/set-config!
    [:appenders :rotor]
    {:min-level :info
     :enabled? true
     :async? false  ; Should be always false for rotor.
     :max-message-per-msecs nil
     :fn rotor/append})
  
  (timbre/set-config!
    [:shared-appender-config :rotor]
    {:path "toxiclink.log" :max-size (* 512 1024) :backlog 10})

  (if-not (schema/initialized?) (schema/create-tables))
  
  (timbre/info "toxiclink started successfully"))

(defn destroy
  "Called when application shuts down."
  []
  (timbre/info "toxiclink is shutting down..."))

(def app (middleware/app-handler
           [home-routes app-routes]
           :middleware []
           :access-rules []
           ;; Serialize/deserialize the following data formats.
           :formats [:json-kw :edn]))
