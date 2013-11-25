(ns toxiclink.test.handler
  (:require [clojure.test :refer [deftest testing is]]
            [ring.mock.request :refer [request]]
            [toxiclink.handler :as handler]
            [toxiclink.routes.home :as home]
            [toxiclink.models.db :as db]
            [toxiclink.util :as util]))

(deftest test-app
  (testing "a redirect"
    (with-redefs [db/get-link
                    (fn [token]
                      {:link ({"foo" "http://example.com/1st"
                               "bar" "https://example.org/2nd"}
                              token)})
                  util/token->id identity]
      (let [response (handler/app (request :get "/foo"))]
        (is (= (:status response) 302))
        (is (= ((:headers response) "Location")
               "http://example.com/1st")))))

  (testing "not-found route"
    (let [response (handler/app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
