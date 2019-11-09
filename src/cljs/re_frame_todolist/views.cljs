(ns re-frame-todolist.views
  (:require [re-frame.core :as re-frame]
            [re-frame-todolist.subs :as subs]
            [reagent.core :as reagent]
            [clojure.string :as str]
            [re-frame-todolist.events :as events]))

(defn todo-item [todo]
  [:li (:title todo)])

(defn todo-input []
  (let [!val (reagent/atom "")]
    (fn []
      [:input.new-todo {:type        "text"
                        :value       @!val
                        :placeholder "What needs to be done?"
                        :on-change   #(reset! !val (-> % .-target .-value))
                        :on-key-down #(when (= 13 (.-which %))
                                        (let [title (str/trim @!val)]
                                          (when (seq title)
                                            (re-frame/dispatch [::events/add-todo title]))
                                          (reset! !val "")))}])))

(defn todo-list []
  (let [todos @(re-frame/subscribe [::subs/todos])]
    [:div
     [todo-input]
     [:ul
      (for [todo todos]
        ^{:key (:id todo)}
        [todo-item todo])]]))
