(defn oneTwo [] {:a 12 :b 13})

(let [{:keys [a, b]} (oneTwo)]
  (+ a b))

; (print foo)


(def nums '(1 2 3 4 5))



(def user {:name "Adam"})


(user :name)

(:name user)

(map (fn [num] (* num num)) nums)

(filter even? (map #(* %1 %1) nums))

(let [x (->> nums
          (filter even?)
          (map #(* % %)))]
  x)

(> 2 3)
