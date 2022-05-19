(ns gravie-developer-test.routes.home
  (:require [gravie-developer-test.api :as api]
            [gravie-developer-test.layout :as layout]
            [gravie-developer-test.middleware :as middleware]
            [ring.util.response]))

(defn home-page [request]
  (layout/render request "games_home.html" {:games (api/get-games)}))

(defn search-page [request]
  (layout/render request "search.html"))

(defn search-action [request]
  (layout/render request "search.html" {:games (if (empty? (:params request))
                                                   []
                                                   (api/get-games (:params request)))}))

(defn game-detail-page [request]
  (layout/render request "game_detail.html" {:game (api/get-game (:guid (:params request)))
                                             :checked-out (api/is-checked-out? (:guid (:params request)))}))

(defn check-out [request]
  (api/check-out-game (:guid (:params request)))
  (layout/render request "game_detail.html" {:game (api/get-game (:guid (:params request)))
                                             :checked-out true}))

(defn check-in [request]
  (api/check-in-game (:guid (:params request)))
  (layout/render request "game_detail.html" {:game (api/get-game (:guid (:params request)))
                                             :checked-out false}))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/search_page" {:get search-page}]
   ["/search" {:get search-action}]
   ["/game_detail" {:get game-detail-page}]
   ["/check-out" {:get check-out}]
   ["/check-in" {:get check-in}]])

