(ns music-q.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [music-q.views :as views]
            [music-q.posts :as posts]
            [ring.util.response :as resp]))

(defroutes public-routes
  (GET "/" [] (views/main-page))
  (route/resources "/"))

(defroutes protected-routes
  (GET "/admin" [] (views/admin-page))
  (GET "/admin/add" [] (views/add-post))
  (POST "/admin/create" [& params]
        (do (posts/create params)
          (resp/redirect "/admin")))
  (GET "/admin/:id/edit" [id] (views/edit-post id))
  (POST "/admin/:id/save" [& params]
        (do (posts/save (:id params) params)
          (resp/redirect "/admin"))))

(defroutes app-routes
  public-routes
  protected-routes
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
