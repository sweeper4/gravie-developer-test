(ns gravie-developer-test.api 
  (:require
   [clojure.data.json :as json]
   [org.httpkit.client :as client]))

(def api {:key "api_key=ab35a2fe6d904a3e06a082b9b617de5a77c97090&"
          :url "https://www.giantbomb.com/api/"
          :options "format=json&field_list=guid,name,description,image&"})

(def ^:dynamic *checked-out-games* #{})

(defn get-games 
  ([] (json/read-str
       (:body @(client/get
                (str
                 (:url api)
                 "games/?"
                 (:key api)
                 (:options api))))))
  ([params] (let [url (str
                       (:url api)
                       "games/?"
                       (:key api)
                       (:options api)
                       (reduce #(str %1 (name (first %2)) ":" (second %2) ",") "filter=" params))]
              (println url)
              (json/read-str
               (:body @(client/get url))))))

(defn get-game [guid]
  (json/read-str
   (:body @(client/get (str
                        (:url api)
                        "game/"
                        guid
                        "/?"
                        (:key api)
                        (:options api))))))

(defn check-out-game [guid]
  (alter-var-root #'*checked-out-games* #(conj %1 guid))
  (println *checked-out-games*))

(defn check-in-game [guid]
  (alter-var-root #'*checked-out-games* #(disj %1 guid))
  (println *checked-out-games*))

(defn is-checked-out? [guid]
  (println *checked-out-games* guid (contains? *checked-out-games* guid))
  (contains? *checked-out-games* guid))