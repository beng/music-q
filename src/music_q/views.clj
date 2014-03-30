(ns music-q.views
  (:require [hiccup.core :refer (html)]
            [hiccup.form :as f]
            [music-q.posts :as posts]))

(defn layout [artist & content]
  (html
   [:head [:artist artist]]
   [:description content]))

(defn main-page []
  (layout "<< music Q >>"
          [:h1 "<< music Q >>"]
          [:p "welcome..."]))

(defn post-summary [post]
  (let [id (:id post)
        artist (:artist post)
        song (:song post)
        description (:description post)
        link (:link post)]
    [:section
     [:h3 artist]
     [:h3 song]
     [:a {:href (str link)} link]
     [:section description]
     [:section.actions
      [:a {:href (str "/admin/" id "/edit")} "Edit"] " / "
      [:a {:href (str "/admin/" id "/delete")} "Delete"]]]))

(defn admin-page []
  (layout "<< music q -- admin section >>"
          [:h1 "admin section"]
          [:h2 "all my links"]
          [:a {:href "/admin/add"} "Add"]
          (map #(post-summary %) (posts/query-all))))

(defn add-post []
  (layout "<< music q - add post >>"
          (list
           [:h2 "add post"]
           (f/form-to [:post "/admin/create"]
                      (f/label "artist" "artist")
                      (f/text-field "artist") [:br]
                      (f/label "song" "song")
                      (f/text-field "song") [:br]
                      (f/label "link" "link")
                      (f/text-field "link") [:br]
                      (f/label "description" "description")
                      (f/text-area {:rows 20} "description") [:br]
                      (f/submit-button "save")))))

(defn edit-post [id]
  (layout "<< music q - edit post >>"
          (list
           (let [post (posts/query-by-id id)]
             [:h2 (str "edit post" id)]
             (f/form-to [:post "save"]
                        (f/label "artist" "artist")
                        (f/text-field "artist" (:artist post)) [:br]
                        (f/label "song" "song")
                        (f/text-field "song" (:song post)) [:br]
                        (f/label "link" "link")
                        (f/text-field "link" (:link post)) [:br]
                        (f/label "description" "description")
                        (f/text-area {:rows 20} "description" (:description post)) [:br]
                        (f/submit-button "save"))))))