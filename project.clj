(defproject toxiclink "0.1.0-SNAPSHOT"
  :dependencies
  [[com.h2database/h2 "1.3.174"]
   [ring-server "0.3.1"]
   [com.taoensso/timbre "2.7.1"]
   [markdown-clj "0.9.35"]
   [korma "0.3.0-RC6"]
   [selmer "0.5.2"]
   [org.clojure/clojure "1.5.1"]
   [log4j
    "1.2.17"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]
   [compojure "1.1.6"]
   [com.taoensso/tower "1.7.1"]
   [lib-noir "0.7.5"]
   [com.postspectacular/rotor "0.1.0"]]
  :ring
  {:handler toxiclink.handler/app,
   :init toxiclink.handler/init,
   :destroy toxiclink.handler/destroy}
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.1"]]}}
  :url "http://example.com/FIXME"
  :aot :all
  :plugins [[lein-ring "0.8.7"]]
  :description "FIXME: write description"
  :min-lein-version "2.0.0")
