(ns re-frame-todolist.events
  (:require [re-frame.core :as re-frame]
            [re-frame-todolist.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn- allocate-next-id [todos]
  (-> todos keys last ((fnil inc 0))))

(re-frame/reg-event-db
 ::add-todo
 (fn [db [_ title]]
   (let [id (allocate-next-id (:todos db))]
     (assoc-in db [:todos id] {:id id :title title}))))
