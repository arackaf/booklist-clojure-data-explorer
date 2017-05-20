(defn oneTwo [] {:a 12 :b 13})

(let [{:keys [a, b]} (oneTwo)]
  (+ a b))

(print foo)
