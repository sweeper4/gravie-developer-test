(ns gravie-developer-test.api (:require
  [luminus.http-server :as http]
  [clojure.data.json :as json]
  [org.httpkit.client :as client])
  )

(def api {:key "api_key=ab35a2fe6d904a3e06a082b9b617de5a77c97090&"
          :url "https://www.giantbomb.com/api/"
          :options "format=json&"})

(defn get-games []
  (json/read-str 
   (:body @(client/get (str
                        (:url api)
                        "games/?"
                        (:key api)
                        (:options api))))))

(defn get-game [guid]
  (json/read-str
   (:body @(client/get (str
                        (:url api)
                        "game/"
                        guid
                        "/?"
                        (:key api)
                        (:options api))))))