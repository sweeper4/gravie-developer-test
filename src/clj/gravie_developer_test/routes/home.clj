(ns gravie-developer-test.routes.home
  (:require [clojure.java.io :as io]
            [gravie-developer-test.api :as api]
            [gravie-developer-test.layout :as layout]
            [gravie-developer-test.middleware :as middleware]
            [ring.util.response]))

(defn home-page [request]
  (layout/render request "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn game-home-page [request]
  (layout/render request "games_home.html" {:docs (-> "docs/game-home.md" io/resource slurp)
                                            :games (api/get-games)}))

(defn about-page [request]
  (layout/render request "about.html"))

(defn search-page [request]
  (layout/render request "search.html"))

(defn game-detail-page [request]
  (layout/render request "game_detail.html" {:game (api/get-game (:query-string request))}))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]
   ["/search" {:get search-page}]
   ["/games_home" {:get game-home-page}]
   ["/game_detail" {:get game-detail-page}]])

